# Use the official Node.js v16 image as the base image
FROM node:16-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the package.json and package-lock.json files to the working directory
COPY package*.json ./

# Install the dependencies
RUN npm install --production

# Copy the rest of the files to the working directory
# COPY next.config.js next.config.js
# COPY tailwind.config.js tailwind.config.js
COPY . .
# Build the production version of the Next.js app
RUN npm run build

# Expose port 3000
EXPOSE 3000

# Start the Next.js app
CMD ["npm", "start"]