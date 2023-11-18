# Easy Helper
Easy Helper is a java library to make easier the proccess of creations of "Massive Loading" features.

## Dependencies
To read the excel files, our library uses **org.apache.poi** dependencies, and we have set an easy-massive-loading architecture to implement more easily and rapidly these kind of features often very commom in corporate applications.
```
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
This step is pending maven approval
### 2. Set annotations in your massive-loading class
For example we have created this initial class
```
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
```
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

---
<p align="center">
  <img src="https://raw.githubusercontent.com/darthpetter/logos/master/yequotes%20app/logo_jedi_alobestia.png" alt="Logo" width="40"/>
</p>

