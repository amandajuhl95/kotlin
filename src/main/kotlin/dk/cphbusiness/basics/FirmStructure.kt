package dk.cphbusiness.basics
import java.time.LocalDate

open class Person (
    val firstName: String,
    val lastName: String,
    val dateOfBirth: LocalDate)

    { override fun toString() = "$firstName $lastName is born $dateOfBirth"  }

class Employee (
    firstName: String,
    lastName: String,
    dateOfBirth: LocalDate,
    val dateOfEmployment: LocalDate,
    val salary: Int,
    department: Department
    ) : Person(firstName, lastName, dateOfBirth)
    {
    var department: Department = department
        get() = field
        set(value) {
            // removes employee from department
            field.employeeList.remove(this)
            field = value
            // add employee to department
            field.employeeList.add(this)

        }

    init { this.department.employeeList.add(this) }

    }

class Department (
    val code: String,
    val name: String)
    {
        // lateinit is another way of setting the manager to null or initialize a value.
        lateinit var manager: Employee
        // internal værdier kan læses i fra samme modul
        internal val employeeList = mutableListOf<Employee>()

        val employees : List<Employee>
            get() = employeeList
    }

fun main() {

    // Departments
    val adm = Department("ADM", "Administration")
    val it = Department("IT", "Information Tech")

    // Employees
    val kurt = Employee("Kurt", "Hansen", LocalDate.of(1900, 2, 2), LocalDate.of(2016, 12, 24),50_000_00, adm)
    val sonja = Employee("Sonja", "Jensen", LocalDate.of(1992, 4, 6 ), LocalDate.of(2010, 11, 14), 35_0000, adm)
    val ib = Employee("Ib", "Bo", LocalDate.of(1959,11, 14), LocalDate.of(1980, 10, 1), 40_000_00, adm)

    // Adds employee as manager to a department
    adm.manager = kurt
    it.manager = sonja

    println("ADM: ${adm.code} is ${adm.name} has ${adm.manager} as manager")
    println("IT: ${it.code} is ${it.name} has ${it.manager} as manager")

    // Prints Employees
    println("Hello ${kurt.firstName} ${kurt.lastName}")
    println("Sonja: $sonja ${sonja.salary} ${sonja.department.name}")
    println("Ib: $ib ${ib.salary} ${ib.department.name}")

    // Prints the employees from ADM department
    println("${adm.code} ${adm.name}:")
    for(employee in adm.employees)
    {
        println(employee)
    }

    // Adds Sonja as employee to the IT department
    sonja.department = it

    // Prints the employees from IT department
    println("${it.code} ${it.name}:")
    for(employee in it.employees)
    {
        println(employee)
    }
}