# Easy Massive Loading

This is a java library to make easier the proccess of creations of "Massive Loading" features.

## Dependencies

To read the excel files, our library uses **org.apache.poi** dependencies, and we have set an easy-massive-loading architecture to implement more easily and rapidly these kind of features often very commom in corporate applications.

```xml
<dependency>
  <groupId>org.apache.poi</groupId>
  <artifactId>poi</artifactId>
  <version>5.2.4</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.4</version>
</dependency>
```

## How to Use

### 1. Import our maven dependence

```xml
<dependency>
    <groupId>io.github.darthpetter</groupId>
    <artifactId>easy-massive-loading</artifactId>
    <version>1.0.5</version>
</dependency>
```

### 2. Set annotations in your massive-loading class

For example we have created this initial class

```java
public class Empleado {
    private int edad;

    private String nombre;

    private String apellido;

    private Boolean activo;

    public Empleado() {

    }

    // gettes and setters
}
```

In the class declaration we need to include the @MassiveLoading annotation, and for each column used in the operation, the @Header annotation should be added.
**The class must be has a constructor with no args**

```java
@MassiveLoading
public class Empleado {
    @Header(order = 1, label = "Edad")
    private int edad;

    @Header(order = 2)
    private String nombre;

    @Header(order = 3, label = "Apellido")
    private String apellido;

    @Header(order = 3, label = "Activo")
    private Boolean activo;
    public Empleado() {

    }

    // gettes and setters 
}
```

In @Header is optional to pass order and label:

* When **order is not defined** the library will create the headers in the order in which each attribute was declared.
* If **label is not defined**, it defaults to the name of the attribute

### 3. Reading or writing files

#### Reading

We inject in our application controller/service, the ExcelReadingImpl class.

The read() function transforms the file into a List of the model provided as the second argument and expects two parameters:

* 1. An InputStream instance of the file to be read.
* 2. The class into which the file will be transformed

```java
public class App{
  private ExcelReadingImpl excelReading;

  // Using dependencies injection
  public App(ExcelReadingImpl excelReading)
  {
    this.excelReading = excelReading;
  }

  public List<Empleado> readFile(InputStream fileInputStream){
    InnerResponse<List<Empleado>> readingResponse=this.excelReading.read(fileInputStream,Empleado.class);
    if(!readingResponse.getOk())
    {
      //fails()
      return null;
    }
    return readingResponse.getData();
  }

}
```


#### Writing

We inject in our application controller/service, the ExcelWritingImpl class.

The write() function generates a byte array from a file, incorporating the headers specified in the model and a list of instances of that model. This results in the creation of an Excel file in the form of a byte array.

* 1) The class into which the file will be written.
* 2) An optional list of instances of the class provided earlier.

```java
public class App{
  private ExcelWritingImpl excelWriting;

  private List<Empleado> empleados = new ArrayList<>();

  // Using dependencies injection
  public App(ExcelWritingImpl excelWriting)
  {
    this.excelWriting = excelWriting;

    Empleado empleado1 = new Empleado();
    empleado1.setEdad(25);
    empleado1.setNombre("Juan");
    empleado1.setApellido("Perez");
    empleado1.setActivo(true)
    Empleado empleado2 = new Empleado();
    empleado2.setEdad(30);
    empleado2.setNombre("Maria");
    empleado2.setApellido("Gomez");
    empleado2.setActivo(false)

    empleados.add(empleado1);
    empleados.add(empleado2);
  }

  public InnerResponseDTO<byte[]> write(){
    InnerResponseDTO<byte[]> response=this.excelWriting.write(Empleado.class,List<Empleado> empleados);

    if(!readingResponse.getOk())
    {
      //fails()
      return null;
    }
    return response.getData();
  }

}
```

---
<p align="center">
  <img src="https://raw.githubusercontent.com/darthpetter/logos/master/yequotes%20app/logo_jedi_alobestia.png" alt="Logo" width="40"/>
</p>
