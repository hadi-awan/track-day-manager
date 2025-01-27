// src/components/session/ActiveSession.tsx
import { useEffect, useState } from 'react';
import { Box, Grid, VStack, Heading, Text, Button, useToast } from '@chakra-ui/react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import {webSocketService} from "../../services/websocketService";

interface SessionData {
    id: number;
    startTime: string;
    status: string;
    currentSpeed: number;
    currentLap: number;
    lapTimes: Array<{
        lapNumber: number;
        time: string;
        avgSpeed: number;
    }>;
}

const ActiveSession = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const toast = useToast();
    const [session, setSession] = useState<SessionData | null>(null);
    const [isTracking, setIsTracking] = useState(false);

    useEffect(() => {
        if (isTracking && session) {
            webSocketService.connect(session.id.toString(), handleLocationUpdate);
            startGPSTracking();

            return () => {
                webSocketService.disconnect();
                stopGPSTracking();
            };
        }
    }, [isTracking, session]);

    const handleLocationUpdate = (data: any) => {
        setSession(prevSession => ({
            ...prevSession!,
            currentSpeed: data.speed,
            currentLap: data.lapNumber,
            lapTimes: data.lapTimes
        }));
    };

    const fetchSessionData = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get(`http://localhost:8080/api/sessions/${id}`, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setSession(response.data);
        } catch (error) {
            toast({
                title: 'Error',
                description: 'Failed to fetch session data',
                status: 'error',
                duration: 3000,
                isClosable: true,
            });
        }
    };

    const startTracking = () => {
        setIsTracking(true);
        // Connect to WebSocket here
    };

    const stopTracking = async () => {
        setIsTracking(false);
        try {
            const token = localStorage.getItem('token');
            await axios.post(`http://localhost:8080/api/sessions/${id}/end`, {}, {
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate('/');
        } catch (error) {
            toast({
                title: 'Error',
                description: 'Failed to end session',
                status: 'error',
                duration: 3000,
                isClosable: true,
            });
        }
    };


    if (!session) return <Box>Loading...</Box>;

    return (
        <Box p={8}>
            <Grid templateColumns="1fr 1fr" gap={8}>
                <VStack align="stretch" spacing={4}>
                    <Heading size="md">Current Session</Heading>
                    <Text>Speed: {session.currentSpeed?.toFixed(1) || 0} km/h</Text>
                    <Text>Current Lap: {session.currentLap || 0}</Text>
                    <Button
                        colorScheme={isTracking ? 'red' : 'green'}
                        onClick={isTracking ? stopTracking : startTracking}
                    >
                        {isTracking ? 'Stop Tracking' : 'Start Tracking'}
                    </Button>
                </VStack>

                <VStack align="stretch" spacing={4}>
                    <Heading size="md">Lap Times</Heading>
                    {session.lapTimes?.map((lap) => (
                        <Box key={lap.lapNumber} p={4} borderWidth={1} borderRadius="md">
                            <Text>Lap {lap.lapNumber}</Text>
                            <Text>Time: {lap.time}</Text>
                            <Text>Avg Speed: {lap.avgSpeed.toFixed(1)} km/h</Text>
                        </Box>
                    ))}
                </VStack>
            </Grid>
        </Box>
    );
};

export default ActiveSession;