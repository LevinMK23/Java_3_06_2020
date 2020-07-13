package lesson7;

import lombok.Data;

@Data
@Table(name = "students")
public class Student {

    @TableField(sqlType = "TEXT")
    private String name;
    @TableField(sqlType = "INTEGER")
    private int groupId;
    @TableField(sqlType = "INTEGER")
    private int studentId;

    public Student(String name, int groupId, int studentId) {
        this.name = name;
        this.groupId = groupId;
        this.studentId = studentId;
    }
}
