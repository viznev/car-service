package com.example.demo.controller;

import com.example.demo.exception.CarNotFoundException;
import com.example.demo.exception.ServerException;
import com.example.demo.model.Car;
import com.example.demo.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CarControllerTest {
    private CarController carController;
    // mock out the dependencies
    @Mock
    private CarService carService;

    @BeforeEach
    public void init() {
        // initialize the mocks
        MockitoAnnotations.initMocks(this);
        this.carController = new CarController(carService);
    }

    @Test
    public void givenValidCar_whenAddCarIsCalled_thenDoesNotReturnNull() {
        // Arrange
        Car car = new Car();
        when(carService.addCar(car)).thenReturn(car);

        // Act
        Car actual = carController.addCar(car);

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void givenValidCar_whenAddCarIsCalled_thenServiceMethodShouldBeCalledWithCar() {
        // Arrange
        Car car = new Car();

        // Act
        Car actual = carController.addCar(car);

        // Assert
        verify(carService).addCar(car);
    }

    @Test
    public void givenValidCar_whenAddCarIsCalled_thenShouldReturnCarFromService() {
        // Arrange
        Car car = new Car();

        Car expected = new Car();
        when(carService.addCar(car)).thenReturn(expected);

        // Act
        Car actual = carController.addCar(car);

        // Assert
        assertEquals(expected, actual);
    }

    /*
    GET / Read
    if given a vin:
        if vin in repo:
            return car object
        else:
            throwServerException
    else if not given a vin and there are cars in the repo:
        return all cars in repo
    else (if no cars in repo):
        throwServerException
     */

    @Test
    public void givenNoVin_whenFindCarIsCalled_thenDoesNotReturnNull() {
        // Act
        List<Car> actual = carController.findCar();

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void givenNoVin_whenFindCarIsCalled_thenCarServiceFindCarIsCalled() {
        // Act
        carController.findCar();

        // Assert
        verify(carService).findCar();
    }

    @Test
    public void givenNoVin_whenFindCarIsCalled_thenListOfCarsReturned() {
        // Arrange
        Car car1 = new Car("vin1", "Hyundai", 2015, "blue");
        Car car2 = new Car("vin2", "Toyota", 2014, "white");
        List<Car> expected = new ArrayList<>();
        expected.add(car1);
        expected.add(car2);
        when(carService.findCar()).thenReturn(expected);

        // Act
        List<Car> actual = carController.findCar();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenNoVinAndNoCarsInRepo_whenFindCarIsCalled_thenThrowServerException() {
        // Arrange
        when(carService.findCar()).thenThrow(new CarNotFoundException());

        // Act/Assert
        assertThrows(ServerException.class, () -> this.carController.findCar());
    }

    @Test
    public void givenAVin_whenFindCarIsCalled_thenDoesNotReturnNull() {
        // Arrange
        String vin = "vin1";
        Car expected = new Car();
        when(carService.findCar(vin)).thenReturn(expected);

        // Act
        Car actual = carController.findCar(vin);

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void givenAVin_whenFindCarIsCalled_thenCarServiceFindCarIsCalled() {
        // Arrange
        String vin = "vin1";

        // Act
        Car actual = carController.findCar(vin);

        // Assert
        verify(carService).findCar(vin);
    }

    @Test
    public void givenAVin_whenFindCarIsCalled_thenReturnsCar() {
        // Arrange
        String vin = "vin1";
        Car expected = new Car("vin1", "Honda", 2008, "Grey");
        when(carService.findCar(vin)).thenReturn(expected);

        // Act
        Car actual = carController.findCar(vin);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenNoVinMatchFoundInRepo_whenFindCarIsCalled_thenThrowServerException() {
        // Arrange
        String vin = "vin1";
        when(carService.findCar(vin)).thenThrow(new CarNotFoundException());

        // Act/Assert
        assertThrows(ServerException.class, () -> this.carController.findCar(vin));
    }
}
