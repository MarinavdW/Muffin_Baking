#!/bin/bash

echo "🧁 Starting Muffin Board Development Environment"
echo "==============================================="

# Check prerequisites
echo "Checking prerequisites..."

if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Please install Java 17 or higher."
    exit 1
fi

if ! command -v mvn &> /dev/null; then
    echo "❌ Maven not found. Please install Maven 3.6+."
    exit 1
fi

if ! command -v node &> /dev/null; then
    echo "❌ Node.js not found. Please install Node.js 16+."
    exit 1
fi

echo "✅ Prerequisites check passed!"
echo ""

# Start backend
echo "🚀 Starting Spring Boot backend..."
cd backend
mvn clean spring-boot:run &
BACKEND_PID=$!

# Wait for backend to start
echo "⏳ Waiting for backend to start..."
sleep 15

# Start frontend
echo "🚀 Starting React frontend..."
cd ../frontend

# Install dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo "📦 Installing frontend dependencies..."
    npm install
fi

npm start &
FRONTEND_PID=$!

echo ""
echo "✅ Development environment started!"
echo "Backend: http://localhost:8080"
echo "Frontend: http://localhost:3000"
echo ""
echo "To stop both servers, press Ctrl+C"

# Cleanup function
cleanup() {
    echo ""
    echo "🛑 Stopping servers..."
    kill $BACKEND_PID 2>/dev/null
    kill $FRONTEND_PID 2>/dev/null
    exit 0
}

trap cleanup INT

# Wait for both processes
wait