# ETAPA 1: Construir la app Angular
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
# Construye para producción
RUN npm run build

# ETAPA 2: Servir con Nginx
FROM nginx:alpine
# Copiamos el build de Angular a la carpeta de Nginx
# OJO: Verifica si tu carpeta dist se llama 'frontend' o el nombre de tu proyecto
COPY --from=build /app/dist/*/browser /usr/share/nginx/html
# Copiamos nuestra configuración personalizada
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
