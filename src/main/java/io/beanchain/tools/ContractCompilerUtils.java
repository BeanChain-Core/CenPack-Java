package io.beanchain.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.tools.*;
import java.util.Arrays;

public class ContractCompilerUtils {

    public static byte[] readClassBytes(File classFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(classFile)) {
            byte[] bytes = new byte[(int) classFile.length()];
            int read = fis.read(bytes);
            if (read != classFile.length()) {
                throw new IOException("Could not read entire file.");
            }
            return bytes;
        }
    }

    public static boolean compileContract(String javaFilePath, String outputDirPath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            System.err.println("No Java compiler found. Are you running a JRE instead of a JDK?");
            return false;
        }

        File javaFile = new File(javaFilePath);
        String className = javaFile.getName().replace(".java", ".class");

        // Temporary build directory
        File tempOutputDir = new File("temp-build");
        tempOutputDir.mkdirs();

        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null)) {
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(tempOutputDir));

            Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(Arrays.asList(javaFile));

            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
            boolean success = task.call();

            if (!success) return false;

            // Find the compiled class file inside temp dir
            Path compiledClass = Files.walk(tempOutputDir.toPath())
                .filter(p -> p.getFileName().toString().equals(className))
                .findFirst()
                .orElseThrow(() -> new IOException("Compiled .class file not found"));

            // Move to flat compiled-contracts directory
            Files.createDirectories(Paths.get(outputDirPath));
            Path targetPath = Paths.get(outputDirPath, className);
            Files.move(compiledClass, targetPath, StandardCopyOption.REPLACE_EXISTING);

            // Clean up temp build dir
            deleteDirectory(tempOutputDir);
            System.out.println("Saved: " + targetPath.toString());
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void deleteDirectory(File dir) throws IOException {
        if (dir.exists()) {
            Files.walk(dir.toPath())
                .map(Path::toFile)
                .sorted((a, b) -> -a.compareTo(b)) // delete children first
                .forEach(File::delete);
        }
    }
}
