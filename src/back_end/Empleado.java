/*
ARIEL VILLA OLIVAREZ
ANGEL EMILIANO REYNOSO LÓPEZ
JULIAN EMMANUEL MENDEZ RODRIGUEZ
 */
package back_end;

public class Empleado {
    private Integer id;
    private String nombre;
    private String puesto;
    private String departamento;
    private Double salario;

    public Empleado(Integer id, String nombre, String puesto, String departamento, Double salario) {
        this.id = id;
        this.nombre = nombre;
        this.puesto = puesto;
        this.departamento = departamento;
        this.salario = salario;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + nombre + " | " + puesto + " | " + departamento + " | $" + salario;
    }
}