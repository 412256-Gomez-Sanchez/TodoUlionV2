package tht.adaptive.ApiUlion.compartidos;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ManejarArchivo {
    private ManejarArchivo(){

    }

    public static String guardar(MultipartFile archivo, String subCarpeta) throws IOException {
        // Obtener la ruta del directorio de trabajo actual
        String directorioRaiz = System.getProperty("user.dir");

        // Construir la ruta completa a la carpeta de uploads y la subcarpeta
        Path rutaCompleta = Paths.get(directorioRaiz, "uploads", subCarpeta);

        // Crear las carpetas si no existen
        if (!Files.exists(rutaCompleta)) {
            Files.createDirectories(rutaCompleta);
        }

        // Generar un nombre de archivo único para evitar colisiones
        String nombreOriginal = archivo.getOriginalFilename();
        String nombreArchivo = System.currentTimeMillis() + "_" + nombreOriginal;
        Path rutaArchivoFinal = rutaCompleta.resolve(nombreArchivo);

        // Guardar el archivo
        Files.write(rutaArchivoFinal, archivo.getBytes());

        return nombreArchivo;
    }
}
