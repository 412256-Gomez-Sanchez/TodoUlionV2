package tht.adaptive.ApiUlion.compartidos;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ManejarArchivo {
    public String guardar(MultipartFile archivo, Path directorio) throws IOException {
        byte[] bytes = archivo.getBytes();

        // Generar un nombre de archivo único
        String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
        Path directorioArchivo = directorio.resolve(nombreArchivo);

        // Guardar el archivo
        //Files.copy(archivo.getInputStream(), directorioArchivo);
        Files.write(directorioArchivo,bytes);

        return nombreArchivo;
    }
}
