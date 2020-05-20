package com.example.demo.service;

import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CarServiceTest {
    private CarService carService;
    @Mock
    private CarRepository carRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.carService = new CarService(carRepository);
    }

    private Car initializeCar() {
        Car car = new Car();
        car.setVin("vin1");
        car.setModelYear(1900);
        return car;
    }

    /*

    POST / Create

    returns not Null & returns a Car
    givenCarWithoutVin thenThrowException
    givenCarModelYearLessThan1900 thenThrowException
    givenCarWithVinAlreadyInRepo thenThrowException

     */

    @Test
    public void givenValidCar_whenAddCarIsCalled_thenDoesNotReturnNull() {
        // Arrange
        Car car = initializeCar();
        when(carRepository.save(car)).thenReturn(car);

        // Act
        Car actual = carService.addCar(car);

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void givenCarWithoutVin_whenAddCarIsCalled_thenThrowException() {
        // Arrange
        Car car = initializeCar();
        car.setVin(null);

        // Act/Assert
        assertThrows(RuntimeException.class, () -> carService.addCar(car));
    }

    @Test
    public void givenModelYearLessThan1900_whenAddCarIsCalled_thenThrowException() {
        // Arrange
        Car car = initializeCar();
        car.setModelYear(1899);

        // Act/Assert
        assertThrows(RuntimeException.class, () -> carService.addCar(car));
    }

    @Test
    public void givenValidCar_whenAddCarIsCalled_thenReturnCarFromRepository() {
        // Arrange
        Car car = initializeCar();
        Car expected = new Car();
        when(carRepository.save(car)).thenReturn(expected);

        // Act
        Car actual = carService.addCar(car);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenCarWithVinAlreadyInRepo_whenAddCarIsCalled_thenThrowException() {
        // Arrange
        Car car = initializeCar();
        when(carRepository.findById(car.getVin())).thenReturn(Optional.of(car));

        // Act/Assert
        assertThrows(RuntimeException.class, () -> carService.addCar(car));
    }

    /*

    GET / Read

    givenValidCar_doesNotReturnNull/returnsCar
    givenNoVin_returnsListOfCars
    givenVinDoesNotExistInRepo_thenThrowException

    findAll:
    givenAnyCar_whenAddIsCalled
    calling service
    not null
    returns lists
    get all returns nothing from db b/c nothing exists

    findById:
    not null
    is car
    calls service
    noVIN throw exception


     */
}
