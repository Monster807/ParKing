package com.Park.services;

import com.Park.controllers.dto.ParkingDto;
import com.Park.controllers.dto.BookingDto;
import com.Park.controllers.dto.UserRegistrationDto;
import com.Park.entities.Parking;
import com.Park.entities.Booking;
import com.Park.entities.Role;
import com.Park.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DtoMapping {

    @Autowired
    private ParkingService parkingService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    //AICI
    public Parking getParkingFromParkingDto(ParkingDto parkingDto)
    {
        Parking parking = new Parking();
        parking.setId(parkingDto.getParkingID());
        parking.setParkingName(parkingDto.getParkingName());
        parking.setCountrey(parkingDto.getCountrey());
        parking.setAdress(parkingDto.getAdress());
        parking.setCity(parkingDto.getCity());
        parking.setStatus(parkingDto.getStatus());
        parking.setParkingImage(null);
        parking.setPpn(parkingDto.getPpn());
        parking.setService(parking.getService());
        parking.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        return parking;
    }

    //AICI
    public ParkingDto getParkingDtoFromParking(Parking parking)
    {
        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setParkingID(parking.getId());
        parkingDto.setParkingName(parking.getParkingName());
        parkingDto.setCountrey(parking.getCountrey());
        parkingDto.setCity(parking.getCity());
        parkingDto.setAdress(parking.getAdress());
        parkingDto.setService(parking.getService());
        parkingDto.setPpn(parking.getPpn());
        parkingDto.setStatus(parking.getStatus());
        parkingDto.setUserName(parking.getUser().getUserName());

        return  parkingDto;
    }

    //AICI
    public Parking updateParkingFromParkingDto(ParkingDto parkingDto)
    {
        Parking parking = parkingService.findById(parkingDto.getParkingID());
        parking.setParkingName(parkingDto.getParkingName());
        parking.setPpn(parkingDto.getPpn());
        return parking;
    }



    public Parking updateParkingStatusFromParkingDto(ParkingDto parkingDto)
    {
        Parking parking = parkingService.findById(parkingDto.getParkingID());
        parking.setStatus(parkingDto.getStatus());
        return parking;
    }

    public User getUserFromUserDto(UserRegistrationDto registration)
    {
        User user = new User();
        user.setId(registration.getId());
        user.setUserName(registration.getUserName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setParkings(null);
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        user.setPhone(registration.getPhone());
        user.setCarReg(registration.getCarReg());

        return user;
    }

    public UserRegistrationDto getUserDtoFromUser(User user)
    {
        UserRegistrationDto dto = new UserRegistrationDto();

        dto.setId(user.getId());

        dto.setUserName(user.getUserName());

        dto.setEmail(user.getEmail());

        dto.setPassword(user.getPassword());
        
        dto.setPhone(user.getPhone());

        dto.setCarReg(user.getCarReg());
        return dto;
    }

    public Booking getBookingFromBookingDto(BookingDto bookingDto)
    {
        Booking booking = new Booking();
        booking.setBookingId(bookingDto.getBookingId());
        booking.setCheckIn(bookingDto.getCheckIn());
        booking.setCheckOut(bookingDto.getCheckOut());
        booking.setInTime(bookingDto.getInTime());
        booking.setOutTime(bookingDto.getOutTime());
        booking.setParking(getParkingFromParkingDto(bookingDto.getParkingDto()));
        booking.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

        return booking;

    }

    public BookingDto getBookingDtoFromBooking(Booking booking)
    {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setBookingId(booking.getBookingId());
        bookingDto.setCheckIn(booking.getCheckIn());
        bookingDto.setCheckOut(booking.getCheckOut());
        bookingDto.setInTime(booking.getInTime());
        bookingDto.setOutTime(booking.getOutTime());
        bookingDto.setParkingDto(getParkingDtoFromParking(booking.getParking()));

        return bookingDto;
    }


}
