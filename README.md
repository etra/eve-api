# Local Development Setup

## Project Overview

This project provides a REST API interface for EVE Online's Static Data Export (SDE). It processes the official EVE Online SDE YAML files and exposes the data through a structured API based on specific requirements.

### SDE Data Source

The project uses EVE Online's SDE YAML files which can be downloaded from the [official EVE Online SDE repository](https://developers.eveonline.com/docs/services/sde/). The SDE files are used as the primary data source for the API.

### SDE Configuration

The SDE files can be configured in two ways:

1. **Default Configuration**:
   - Place the SDE zip file in the `sde` directory at the root of the project
   - The `sde` directory is git-ignored for local development
   - For local development, create a `local.properties` file to override default settings

2. **Custom Configuration**:
   - Specify a custom path to the SDE zip file in the configuration
   - The path should point to the zip file directly (no need to unzip)
   - Configuration can be overridden through environment variables or properties files

### Example Usage

The API will provide various endpoints to access EVE Online data. Examples of available endpoints and their usage will be documented in future updates.

## Prerequisites

- OpenJDK 21
  - Download from [Adoptium](https://adoptium.net/) or use a package manager
  - For Linux: `sudo apt install openjdk-21-jdk`
  - For macOS: `brew install openjdk@21`
  - For Windows: Use the Adoptium installer
- Gradle 8.x (Wrapper included in project)
  - If using local Gradle, download from [Gradle releases](https://gradle.org/releases/)

## Building and Running Locally

### Using Gradle Wrapper (Recommended)

Build the project:

```bash
./gradlew build
```

Run the application:

```bash
./gradlew bootRun
```

```
watchexec -e java -w src -- ./gradlew classes
./gradlew bootrun

Hot reload
```bash
./gradlew watch
```

### Using Local Gradle Installation

If you prefer using a local Gradle installation instead of the wrapper:

Build the project:
```bash
gradle build
```

Run the application:
```bash
gradle bootRun
```

### Additional Gradle Commands

- Clean build directory:
```bash
./gradlew clean
```

- Run tests:
```bash
./gradlew test
```

- Build without running tests:
```bash
./gradlew build -x test
```

- Generate dependency tree:
```bash
./gradlew dependencies
```

### Troubleshooting

If you encounter any issues:

1. Make sure you have the correct Java version installed:
```bash
java -version
```

2. Clean and rebuild the project:
```bash
./gradlew clean build
```

3. Check Gradle wrapper version:
```bash
./gradlew --version
```

# EVE Online API

This project provides an API for accessing EVE Online's Static Data Export (SDE) files.

## Development

### Prerequisites

- Java 21 or later
- Gradle 8.0 or later

### Building

To build the project:

```bash
./gradlew build
```

### Running

To run the application:

```bash
./gradlew bootRun
```

### Testing

To run tests:

```bash
./gradlew test
```

## Deployment

### Building the Deployment Package

To create a deployment package:

```bash
./gradlew buildFatJar
```

This will create a fat jar in `build/dist/eve-api.jar`.

### Server Setup

1. Create the application user and group:
   ```bash
   sudo useradd -r -s /bin/false eve-api
   ```

2. Create the application directory:
   ```bash
   sudo mkdir -p /opt/eve-api
   sudo chown eve-api:eve-api /opt/eve-api
   ```

3. Copy the deployment files:
   ```bash
   sudo cp build/dist/eve-api.jar /opt/eve-api/
   sudo cp deploy/eve-api.service /etc/systemd/system/
   ```

4. Enable and start the service:
   ```bash
   sudo systemctl daemon-reload
   sudo systemctl enable eve-api
   sudo systemctl start eve-api
   ```

### Service Management

- Check service status:
  ```bash
  sudo systemctl status eve-api
  ```

- View logs:
  ```bash
  sudo journalctl -u eve-api
  ```

- Restart service:
  ```bash
  sudo systemctl restart eve-api
  ```

### Directory Structure

```
/opt/eve-api/
├── eve-api.jar
└── logs/           # Application logs (created automatically)
```

## API Endpoints

### Categories

- `GET /api/categories` - List all categories
- `GET /api/categories/{id}` - Get category by ID

### Market Groups

- `GET /api/market-groups` - List all market groups
- `GET /api/market-groups/{id}` - Get market group by ID

### Industry

- `GET /api/industry/blueprint/{typeId}` - Get blueprint details for a type ID
- `GET /api/industry/requirements/{typeId}` - Get all required materials for building an item







