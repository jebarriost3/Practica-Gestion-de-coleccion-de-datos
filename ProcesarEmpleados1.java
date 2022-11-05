
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//importar optional 
import java.util.Optional;

public class ProcesarEmpleados1{
    // Procesamiento de flujos de objetos Empleado.

    public static void main(String[] args) {
     
String filePatch = "C:\\Users\\juane\\Downloads\\prepractica-main_vA\\prepractica-main\\materialpractica\\empleado.csv";
List<Empleado> empl = new ArrayList<>();
try (Stream<String> streamFile = Files.lines(Paths.get(filePatch))){
     empl=  streamFile.map(linea -> linea.split(";")).map(arreglo -> {
                   Empleado ele = new Empleado(Integer.parseInt(arreglo[0]),arreglo[1],arreglo[2],Double.parseDouble(arreglo[3]),arreglo[4]);
                   return ele;
      }).collect(Collectors.toList());

//       empl.forEach(System.out::println);
} catch (IOException e) {
      
       System.out.println("ERROR EN LECTURA DE ARCHIVO");
}

        // obtiene vista List de los objetos Empleadop

        System.out.println("-----------------------------------------------------------------------------------------------------------------");
       
        // muestra todos los objetos Empleado
        System.out.println("Lista completa de empleados:");
        empl.stream().forEach(System.out::println);

        // Predicado que devuelve true para salarios en el rango $4000-$6000
        Predicate<Empleado> cuatroASeisMil =
                e -> (e.getSalario() >= 4000 && e.getSalario() <= 6000);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // Muestra los empleados con salarios en el rango $4000-$6000
        // en orden ascendente por salario
        System.out.printf(
                "%nEmpleados que ganan $4000-$6000 mensuales ordenados por salario:%n");
        empl.stream()
                .filter(cuatroASeisMil)
                .sorted(Comparator.comparing(Empleado::getSalario))
                .forEach(System.out::println);
                System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // Muestra el primer empleado con salario en el rango $4000-$6000
        System.out.printf("%nPrimer empleado que gana $4000-$6000:%n%s%n",
                empl.stream()
                        .filter(cuatroASeisMil)
                        .findFirst()
                        .get());
        // Funciones para obtener primer nombre y apellido de un Empleado
        Function<Empleado, String> porPrimerNombre = Empleado::getPrimerNombre;
        Function<Empleado, String> porApellidoPaterno = Empleado::getApellidoPaterno;

        // Comparator para comparar empleados por primer nombre y luego por apellido paterno
        Comparator<Empleado> apellidoLuegoNombre =
                Comparator.comparing(porApellidoPaterno).thenComparing(porPrimerNombre);
                System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // ordena empleados por apellido paterno y luego por primer nombre
        System.out.printf(
                "%nEmpleados en orden ascendente por apellido y luego por nombre:%n");
        empl.stream()
                .sorted(apellidoLuegoNombre)
                .forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // ordena empleados en forma descendente por apellido, luego por nombre
        System.out.printf(
                "%nEmpleados en orden descendente por apellido y luego por nombre:%n");
        empl.stream()
                .sorted(apellidoLuegoNombre.reversed())
                .forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        // muestra apellidos de empleados únicos ordenados
        System.out.printf("%nApellidos de empleados unicos:%n");
        empl.stream()
                .map(Empleado::getApellidoPaterno)
                .distinct()
                .sorted()
                .forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // muestra sólo nombre y apellido
        System.out.printf(
                "%nNombres de empleados en orden por apellido y luego por nombre:%n");
        empl.stream()
                .sorted(apellidoLuegoNombre)
                .map(Empleado::getPrimerNombre)
                .forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // agrupa empleados por departamento
        System.out.printf("%nEmpleados por departamento:%n");
        Map<String, List<Empleado>> agrupadoPorDepartamento =
                empl.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento));
        agrupadoPorDepartamento.forEach(
                (departamento, empleadosEnDepartamento) ->
                {
                    System.out.println(departamento);
                    empleadosEnDepartamento.forEach(
                            empleado -> System.out.printf(" %s%n", empleado));
                }
        );

        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // cuenta el número de empleados en cada departamento
        System.out.printf("%nConteo de empleados por departamento:%n");
        Map<String, Long> conteoEmpleadosPorDepartamento =
                empl.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento,
                                TreeMap::new, Collectors.counting()));
        conteoEmpleadosPorDepartamento.forEach(
                (departamento, conteo) -> System.out.printf(
                        "%s tiene %d empleado(s)%n", departamento, conteo));
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // suma de salarios de empleados con el método sum de DoubleStream
        System.out.printf(
                "%nSuma de los salarios de los empleados (mediante el metodo sum): %.2f%n",
                empl.stream()
                        .mapToDouble(Empleado::getSalario)
                        .sum());
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // calcula la suma de los salarios de los empleados con el método reducede Stream
        System.out.printf("Suma de los salarios de los empleados (mediante el metodo reduce): %.2f%n",
                empl.stream()
                        .mapToDouble(Empleado::getSalario)
                        .reduce(0, (valor1, valor2) -> valor1 + valor2));
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // promedio de salarios de empleados con el método average de DoubleStream
        System.out.printf("Promedio de salarios de los empleados: %.2f%n",
                empl.stream()
                        .mapToDouble(Empleado::getSalario)
                        .average()
                        .getAsDouble());
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
   // Colección de empleados  cuyo salario se encuentre en un rango determinado.
        System.out.printf("%nEmpleados cuyo salario esta en el rango de $2000-$5000:%n");
        empl.stream()
                .filter(e -> e.getSalario() >= 2000 && e.getSalario() <= 5000)
                .sorted(Comparator.comparing(Empleado::getSalario))
                .forEach(System.out::println);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
//Colección de empleados que pertenecen a cada departamento. 
System.out.println("Empleados por departamento");
        Map<String, List<Empleado>> empleadosPorDepartamento =
                empl.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento));
        empleadosPorDepartamento.forEach(
                (departamento, empleadosEnDepartamento) ->
                {
                    System.out.println(departamento);
                    empleadosEnDepartamento.forEach(
                            empleado -> System.out.printf(" %s%n", empleado));
                }
        );
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        //cantidad de empleados por departamento
        System.out.println("Cantidad de empleados por departamento");
        Map<String, Long> cantidadEmpleadosPorDepartamento =
                empl.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento,
                                TreeMap::new, Collectors.counting())); 
        cantidadEmpleadosPorDepartamento.forEach( 
                (departamento, cantidad) -> System.out.printf(
                        "%s tiene %d empleado(s)%n", departamento, cantidad)); 

        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        //Sumatoria de la nomina por departamento
        System.out.println("Sumatoria de la nomina por departamento");
        Map<String, Double> sumatoriaNominaPorDepartamento =
                empl.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento,
                                TreeMap::new, Collectors.summingDouble(Empleado::getSalario)));
        sumatoriaNominaPorDepartamento.forEach( 
                (departamento, sumatoria) -> System.out.printf(
                        "%s tiene una sumatoria de %.2f%n", departamento, sumatoria));
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
                         
                         
        //Nombre del empleado que mas gana en cada departamento

        System.out.println("Nombre del empleado que mas gana en cada departamento");
        Map<String, Optional<Empleado>> nombreEmpleadoQueMasGanaPorDepartamento =
                empl.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento,
                                TreeMap::new, Collectors.maxBy(Comparator.comparing(Empleado::getSalario))));
        nombreEmpleadoQueMasGanaPorDepartamento.forEach( 
                (departamento, empleado) -> System.out.printf(
                        "%s tiene como empleado que mas gana a %s%n", departamento, empleado.get().getPrimerNombre()));
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        // Nombre del empleado que mayor salario recibe (de todos).
        System.out.println("Nombre del empleado que mayor salario recibe (de todos)");
        Optional<Empleado> empleadoQueMasGana =
                empl.stream()
                        .collect(Collectors.maxBy(Comparator.comparing(Empleado::getSalario)));
        System.out.println(empleadoQueMasGana.get().getPrimerNombre());
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        //Nombre del empleado que menor salario recibe (de todos).
        System.out.println("Nombre del empleado que menor salario recibe (de todos)");
        Optional<Empleado> empleadoQueMenosGana =
                empl.stream()
                        .collect(Collectors.minBy(Comparator.comparing(Empleado::getSalario)));
        System.out.println(empleadoQueMenosGana.get().getPrimerNombre());

        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        //Promedio de salario por departamento.
        System.out.println("Promedio de salario por departamento");
        Map<String, Double> promedioSalarioPorDepartamento =
                empl.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento,
                                TreeMap::new, Collectors.averagingDouble(Empleado::getSalario)));
        promedioSalarioPorDepartamento.forEach(         
                (departamento, promedio) -> System.out.printf(
                        "%s tiene un promedio de %.2f%n", departamento, promedio));

        System.out.println("-----------------------------------------------------------------------------------------------------------------");
//Promedio salario general.
        System.out.println("Promedio salario general");
        Double promedioSalarioGeneral =
                empl.stream()
                        .collect(Collectors.averagingDouble(Empleado::getSalario));
        System.out.println(promedioSalarioGeneral);
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        //Valor total de la nómina.
        System.out.println("Valor total de la nómina");
        Double valorTotalNomina =
                empl.stream()
                        .collect(Collectors.summingDouble(Empleado::getSalario));
        System.out.println(valorTotalNomina);
    } // fin de main
    public void lee(){
      
   
       }
} // fin de la clase Procesar empleados

