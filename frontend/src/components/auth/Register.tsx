// src/components/auth/Register.tsx
import { useState } from 'react';
// @ts-ignore
import { Box, Button, FormControl, FormLabel, Input, VStack, Heading, useToast } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Register = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const navigate = useNavigate();
    const toast = useToast();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/api/auth/register', {
                email,
                password,
                name
            });
            toast({
                title: 'Success',
                description: 'Registration successful! Please login.',
                status: 'success',
                duration: 3000,
                isClosable: true,
            });
            navigate('/login');
        } catch (error) {
            toast({
                title: 'Error',
                description: 'Registration failed',
                status: 'error',
                duration: 3000,
                isClosable: true,
            });
        }
    };

    // @ts-ignore
    // @ts-ignore
    return (
        <Box p={8}>
            <VStack spacing={4} align="stretch" maxW="md" mx="auto">
                <Heading>Register</Heading>
                <form onSubmit={handleSubmit}>
                    <VStack spacing={4}>
                        <FormControl>
                            <FormLabel>Name</FormLabel>
                            <Input
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                            />
                        </FormControl>
                        <FormControl>
                            <FormLabel>Email</FormLabel>
                            <Input
                                type="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </FormControl>
                        <FormControl>
                            <FormLabel>Password</FormLabel>
                            <Input
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </FormControl>
                        <Button type="submit" colorScheme="blue" width="full">
                            Register
                        </Button>
                    </VStack>
                </form>
            </VStack>
        </Box>
    );
};

export default Register;