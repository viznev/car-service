package com.example.demo.service;

import com.example.demo.exception.CarNotFoundException;
import com.example.demo.model.Car;
import com.example.demo.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
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

    private List<Car> initCarList() {
        Car car1 = new Car("vin1", "Honda", 2008, "Grey");
        Car car2 = new Car("vin2", "Toyota", 2014, "white");
        List<Car> carList = new ArrayList<>();
        carList.add(car1);
        carList.add(car2);
        return carList;
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
    if given a vin:
        if vin in repo:
            return car object
        else:
            throwCarNotFoundException
    else if not given a vin and there are cars in the repo:
        return all cars in repo
    else (if no cars in repo):
        throwCarNotFoundException

        vin:
            returnsNotNull&typeCar
            carRepository.findById(vin) is called
            returnsCarFromRepo
            ifVinNotInRepoThrowsException

        noVin:
            returnsNotNull&typeArrayListCar
            carRepository.findAll() is called
            listOfCarsFromRepo
            noCarsInRepoThrowsException
     */

    @Test
    public void givenNoVin_whenFindCarIsCalled_thenDoesNotReturnNull() {
        // Arrange
        List<Car> expected = initCarList();
        when(carRepository.findAll()).thenReturn(expected);

        // Act
        List<Car> actual = carService.findCar();

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void givenNoVin_whenFindCarIsCalled_thenCarRepositoryFindAllIsCalled() {
        // Arrange
        List<Car> expected = initCarList();
        when(carRepository.findAll()).thenReturn(expected);

        // Act
        List<Car> actual = carService.findCar();

        // Assert
        verify(carRepository).findAll();
    }

    @Test
    public void givenNoVin_whenFindCarIsCalled_thenCarRepositoryReturnsListOfCars() {
        // Arrange
        List<Car> expected = initCarList();
        when(carRepository.findAll()).thenReturn(expected);

        // Act
        List<Car> actual = carService.findCar();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenNoVinAndNoCarsInRepo_whenFindCarIsCalled_thenThrowCarNotFoundException() {
        // Arrange
        List<Car> expected = new ArrayList<>();
        when(carRepository.findAll()).thenReturn(expected);

        // Act/Assert
        assertThrows(CarNotFoundException.class, () -> this.carService.findCar());
    }

    @Test
    public void givenAVin_whenFindCarIsCalled_thenDoesNotReturnNull() {
        // Arrange
        String vin = "vin1";
        Car expected = new Car("vin1", "Honda", 2008, "Grey");
        when(carRepository.findById(vin)).thenReturn(Optional.of(expected));

        // Act
        Car actual = carService.findCar(vin);

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void givenAVin_whenFindCarIsCalled_thenFindByIdIsCalled() {
        // Arrange
        String vin = "vin1";
        Car expected = new Car("vin1", "Honda", 2008, "Grey");
        when(carRepository.findById(vin)).thenReturn(Optional.of(expected));

        // Act
        Car actual = carService.findCar(vin);

        // Assert
        verify(carRepository).findById(vin);
    }

    @Test
    public void givenAVin_whenFindCarIsCalled_thenShouldReturnCarFromRepo() {
        // Arrange
        String vin = "vin1";
        Car expected = new Car("vin1", "Honda", 2008, "Grey");
        when(carRepository.findById(vin)).thenReturn(Optional.of(expected));

        // Act
        Car actual = carService.findCar(vin);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void givenAVinNotFoundInRepo_whenFindCarIsCalled_thenThrowCarNotFoundException() {
        // Arrange
        String vin = "vin1";

        // Act/Assert
        assertThrows(CarNotFoundException.class, () -> this.carService.findCar(vin));
    }

    /*
    DELETE
    given a vin:
        if vin in repo:
            delete the entity
        else:
            throwCarNotFoundException

    givenAVin_thenCarRepositoryDeleteShouldBeCalled
    givenAVinThatDoesNotExistInRepo_thenThrowsCarNotFoundException
     */

    @Test
    public void givenAVin_whenDeleteCarIsCalled_thenCarRepositoryDeleteShouldBeCalled() {
        // Arrange
        String vin = "vin1";
        Car car = new Car("vin1", "Honda", 2008, "Grey");
        when(carRepository.findById(vin)).thenReturn(Optional.of(car));

        // Act
        carService.deleteCar(vin);

        // Assert
        verify(carRepository).deleteById(vin);
    }

    @Test
    public void givenAVinThatDoesNotExistInRepo_whenDeleteCarIsCalled_thenThrowsCarNotFoundException() {
        // Arrange
        String vin = "vin1";

        // Act/Assert
        assertThrows(CarNotFoundException.class, () -> this.carService.deleteCar(vin));
    }
}
