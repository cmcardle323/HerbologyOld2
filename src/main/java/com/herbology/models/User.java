package com.herbology.models;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
    @Column(nullable = false, unique = true)
    private String email;
     
    @Column(nullable = false)
    private String password;
     
    @Column(name = "first_name", nullable = false)
    private String firstName;
     
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Plant.class)
	List<Plant> plantList;
    
    public User() {
    	super();
    }
    
    public User(Long id, String firstname, String lastname, String email, String password, List<Plant> plantList) {
		this.id = id;
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.password = password;
		this.plantList = plantList;
	}
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Plant> getPlantList() {
		return plantList;
	}

	public void setPlantList(List<Plant> plantList) {
		this.plantList = plantList;
	}
        
}