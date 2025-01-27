# Track Day Manager

A real-time lap timing and telemetry system for track day events. This application helps drivers monitor their performance, track lap times, and analyze their driving data during track sessions.

## Features

- **Real-time GPS Tracking**
 - Live speed monitoring
 - Position tracking
 - Lap detection

- **Session Management**
 - Create and manage track day sessions
 - Track multiple drivers
 - Historical session data

- **Lap Timing**
 - Automatic lap detection
 - Lap time recording
 - Split times

- **Data Analysis**
 - Speed graphs
 - Lap time comparison
 - Performance statistics

## Technologies Used

- **Backend**
 - Java Spring Boot
 - Spring Security with JWT
 - PostgreSQL
 - WebSocket

- **Frontend**
 - HTML/JavaScript
 - Chart.js for data visualization
 - SockJS and STOMP for WebSocket communication

## Getting Started

### Prerequisites
- Java 17 or higher
- PostgreSQL
- Maven

### Installation
1. Clone the repository
```bash
git clone https://github.com/yourusername/track-day-manager.git
```

### Configure PostgreSQL database in application.properties

### Run the application
```
mvn spring-boot:run
```

## API Documentation
The application provides RESTful APIs for:
- User authentication
- Session management
- Track information
- Lap timing
- Location data

## Future Enhancements
- Track boundary detection
- Mobile app integration
- Weather data integration
- Video synchronization
