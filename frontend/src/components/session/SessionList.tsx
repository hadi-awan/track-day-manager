// src/components/session/SessionList.tsx
import { useEffect, useState } from 'react';
// @ts-ignore
import { Box, Button, Heading, Table, Thead, Tbody, Tr, Th, Td, VStack, useToast } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

interface Session {
    id: number;
    startTime: string;
    endTime: string;
    status: string;
}

const SessionList = () => {
    const [sessions, setSessions] = useState<Session[]>([]);
    const navigate = useNavigate();
    const toast = useToast();

    useEffect(() => {
        fetchSessions();
    }, []);

    const fetchSessions = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:8080/api/sessions', {
                headers: { Authorization: `Bearer ${token}` }
            });
            setSessions(response.data);
        } catch (error) {
            toast({
                title: 'Error',
                description: 'Failed to fetch sessions',
                status: 'error',
                duration: 3000,
                isClosable: true,
            });
        }
    };

    const startNewSession = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.post('http://localhost:8080/api/sessions', {}, {
                headers: { Authorization: `Bearer ${token}` }
            });
            navigate(`/session/${response.data.id}`);
        } catch (error) {
            toast({
                title: 'Error',
                description: 'Failed to start new session',
                status: 'error',
                duration: 3000,
                isClosable: true,
            });
        }
    };

    return (
        <Box p={8}>
            <VStack spacing={4} align="stretch">
                <Heading>Track Sessions</Heading>
                <Button colorScheme="blue" onClick={startNewSession}>
                    Start New Session
                </Button>
                <Table variant="simple">
                    <Thead>
                        <Tr>
                            <Th>ID</Th>
                            <Th>Start Time</Th>
                            <Th>End Time</Th>
                            <Th>Status</Th>
                            <Th>Actions</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                        {sessions.map((session) => (
                            <Tr key={session.id}>
                                <Td>{session.id}</Td>
                                <Td>{new Date(session.startTime).toLocaleString()}</Td>
                                <Td>{session.endTime ? new Date(session.endTime).toLocaleString() : '-'}</Td>
                                <Td>{session.status}</Td>
                                <Td>
                                    <Button size="sm" onClick={() => navigate(`/session/${session.id}`)}>
                                        View
                                    </Button>
                                </Td>
                            </Tr>
                        ))}
                    </Tbody>
                </Table>
            </VStack>
        </Box>
    );
};

export default SessionList;