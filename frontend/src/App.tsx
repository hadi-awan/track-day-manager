import { ChakraProvider } from '@chakra-ui/react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/layout/Navbar';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import SessionList from './components/session/SessionList';
import ActiveSession from './components/session/ActiveSession';

const App = () => {
  const isAuthenticated = !!localStorage.getItem('token');

  return (
      <ChakraProvider>
        <Router>
          <Navbar />
          <Routes>
            <Route path="/login" element={!isAuthenticated ? <Login /> : <Navigate to="/" />} />
            <Route path="/register" element={!isAuthenticated ? <Register /> : <Navigate to="/" />} />
            <Route path="/" element={isAuthenticated ? <SessionList /> : <Navigate to="/login" />} />
            <Route path="/session/:id" element={isAuthenticated ? <ActiveSession /> : <Navigate to="/login" />} />
          </Routes>
        </Router>
      </ChakraProvider>
  );
};

export default App;