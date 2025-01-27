import { Box, Flex, Button, Heading } from '@chakra-ui/react';
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
    const navigate = useNavigate();
    const isAuthenticated = localStorage.getItem('token');

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    return (
        <Flex bg="gray.100" p={4} justify="space-between" align="center">
            <Heading size="md" cursor="pointer" onClick={() => navigate('/')}>
                Track Day Manager
            </Heading>
            <Box>
                {isAuthenticated ? (
                    <Button onClick={handleLogout}>Logout</Button>
                ) : (
                    <Button onClick={() => navigate('/login')}>Login</Button>
                )}
            </Box>
        </Flex>
    );
};

export default Navbar;