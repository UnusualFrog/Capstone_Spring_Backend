package org.example.capstone.controllers;

import org.example.capstone.dataaccess.HomeRepository;
import org.example.capstone.dataaccess.UserRepository;
import org.example.capstone.dataaccess.AutoRepository;
import org.example.capstone.pojos.OLD_Auto;
import org.example.capstone.pojos.OLD_Home;
import org.example.capstone.pojos.OLD_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

/**
 * The main controller for this application, handling RESTful endpoints
 * for User, Home and Auto resources. Controllers are organized by base URL
 * and provide CRUD (Create, Read, Update, Delete) operations.
 */
@Controller
@RequestMapping(path = RESTNouns.VERSION_1)
public class MainController {

    //Wire the ORM
    @Autowired private UserRepository userRepository;
    @Autowired private HomeRepository homeRepository;
    @Autowired private AutoRepository autoRepository;

    /* *
     *  USER METHODS
     * */

    /**
     * Retrieves all users from the database.
     *
     * @return An iterable collection of all User entities
     */
    @GetMapping(path = RESTNouns.USER)
    public @ResponseBody Iterable<OLD_User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a specific user by their unique identifier.
     *
     * @param userId The unique identifier of the user to retrieve
     * @return An Optional containing the User if found, or an empty Optional
     */
    @GetMapping(path = RESTNouns.USER + RESTNouns.ID)
    public @ResponseBody Optional<OLD_User> getUser(@PathVariable("id") Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Creates a new user in the database.
     *
     * @param name The name of the user to create
     * @param email The email address of the user to create
     * @return The newly created User entity
     */
    @PostMapping(path = RESTNouns.USER)
    public @ResponseBody OLD_User createUser(
            @RequestParam String name, @RequestParam String email) {
        OLD_User user = new OLD_User();
        user.setName(name);
        user.setEmail(email);
        return userRepository.save(user);
    }

    /**
     * Deletes a user from the database by their unique identifier.
     *
     * @param userId The unique identifier of the user to delete
     * @return A string message indicating the result of the deletion operation
     */
    @DeleteMapping(path = RESTNouns.USER + RESTNouns.ID)
    public @ResponseBody String deleteUser(@PathVariable("id") Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return "User with ID " + userId + " deleted successfully.";
        } else {
            return "User with ID " + userId + " not found.";
        }
    }

    /**
     * Updates an existing user's information.
     *
     * @param userId The unique identifier of the user to update
     * @param name The new name for the user
     * @param email The new email address for the user
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.USER + RESTNouns.ID)
    public @ResponseBody String updateUser(
            @PathVariable("id") Long userId, @RequestParam String name, @RequestParam String email){
        if (userRepository.existsById(userId)) {
            Optional<OLD_User> user = userRepository.findById(userId);
            if(user.isPresent()){
                user.get().setName(name);
                user.get().setEmail(email);
                userRepository.save(user.get());
            }
            return "User with ID " + userId + " updated successfully.";
        } else {
            return "User with ID " + userId + " not found.";
        }
    }

    /* *
     *  HOME METHODS
     *
     * */

    /**
     * Retrieves all homes associated with a specific user.
     *
     * @param userId The unique identifier of the user whose homes are to be retrieved
     * @return An iterable collection of Home entities belonging to the specified user
     */
    @GetMapping(path = RESTNouns.USER +  RESTNouns.ID + RESTNouns.HOME)
    public @ResponseBody Iterable<OLD_Home> getAllHomesByUser(@PathVariable("id") Long userId) {
        Iterable<OLD_Home> homes = null;
        if (userRepository.existsById(userId)) {
            Optional<OLD_User> user = userRepository.findById(userId);
            if(user.isPresent()){
                homes = homeRepository.getAllByUserId(userId);
            }
        }

        //TODO handle errors

        return homes;
    }

    /**
     * Creates a new home for a specific user.
     *
     * @param userId The unique identifier of the user for whom the home is being created
     * @param dateBuilt The date when the home was built
     * @param value The monetary value of the home
     * @return The newly created Home entity, or null if the user does not exist
     */
    @PostMapping(path = RESTNouns.USER + RESTNouns.ID + RESTNouns.HOME)
    public @ResponseBody OLD_Home createHomeByUser(
            @PathVariable("id") Long userId,
            @RequestParam LocalDate dateBuilt, @RequestParam int value, @RequestParam OLD_Home.HeatingType heatingType, @RequestParam OLD_Home.Location location) {
        OLD_Home home = null;
        if (userRepository.existsById(userId)) {
            Optional<OLD_User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                home = new OLD_Home();
                home.setValue(value);
                home.setDateBuilt(dateBuilt);
                home.setUser(user.get());
                home.setHeatingType(heatingType);
                home.setLocation(location);
                homeRepository.save(home);
            }
        }

        return home;
    }

    /**
     * Updates an existing home associated with a specific user.
     *
     * @param userId The unique identifier of the user who owns the home
     * @param homeId The unique identifier of the home to be updated
     * @param dateBuilt The new date when the home was built
     * @param value The new monetary value of the home
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.USER + RESTNouns.USER_ID + RESTNouns.HOME + RESTNouns.HOME_ID)
    public @ResponseBody String updateHomeByUser(
            @PathVariable("user_id") Long userId, @PathVariable("home_id") Long homeId,
            @RequestParam LocalDate dateBuilt, @RequestParam int value, @RequestParam OLD_Home.HeatingType heatingType, @RequestParam OLD_Home.Location location){
        if (userRepository.existsById(userId) && homeRepository.existsById(homeId)) {
            Optional<OLD_User> user = userRepository.findById(userId);
            Optional<OLD_Home> home = homeRepository.findById(homeId);
            if(user.isPresent() && home.isPresent()){
                home.get().setDateBuilt(dateBuilt);
                home.get().setValue(value);
                home.get().setHeatingType(heatingType);
                home.get().setLocation(location);

                homeRepository.save(home.get());
                userRepository.save(user.get());
            }
            return "Home with ID " + homeId + " updated successfully.";
        } else {
            return "Home with ID " + homeId + " not found.";
        }
    }

    /**
     * Deletes a specific home associated with a user.
     *
     * @param userId The unique identifier of the user who owns the home
     * @param homeId The unique identifier of the home to be deleted
     * @return A string message indicating the result of the deletion operation
     */
    @DeleteMapping(path = RESTNouns.USER + RESTNouns.USER_ID + RESTNouns.HOME + RESTNouns.HOME_ID)
    public @ResponseBody String deleteHomeByUser(
            @PathVariable("user_id") Long userId, @PathVariable("home_id") Long homeId) {
        if (userRepository.existsById(userId) && homeRepository.existsById(homeId)) {
            homeRepository.deleteById(homeId);
            return "Home with ID " + homeId + " deleted successfully.";
        } else {
            return "Home with ID " + homeId + " not found.";
        }
    }

    /* *
     *  AUTO METHODS
     * */

    /**
     * Retrieves all auto objects associated with a specific user.
     *
     * @param userId The unique identifier of the user whose auto objects are to be retrieved
     * @return An iterable collection of Auto entities belonging to the specified user,
     *         or null if the user does not exist
     */
    @GetMapping(path = RESTNouns.USER +  RESTNouns.ID + RESTNouns.AUTO)
    public @ResponseBody Iterable<OLD_Auto> getAllAutosByUser(@PathVariable("id") Long userId) {
        Iterable<OLD_Auto> autos = null;
        if (userRepository.existsById(userId)) {
            Optional<OLD_User> user = userRepository.findById(userId);
            if(user.isPresent()){
                autos = autoRepository.getAllByUserId(userId);
            }
        }
        return autos;
    }

    /**
     * Creates a new auto object for a specific user.
     *
     * @param userId The unique identifier of the user for whom the auto is being created
     * @param dateBuilt The date when the auto was built
     * @param value The monetary value of the auto
     * @return The newly created Auto entity, or null if the user does not exist
     */
    @PostMapping(path = RESTNouns.USER + RESTNouns.ID + RESTNouns.AUTO)
    public @ResponseBody OLD_Auto createAutoByUser(
            @PathVariable("id") Long userId,
            @RequestParam LocalDate dateBuilt, @RequestParam int value) {
        OLD_Auto auto = null;
        if (userRepository.existsById(userId)) {
            Optional<OLD_User> user = userRepository.findById(userId);
            if (user.isPresent()) {
                auto = new OLD_Auto();
                auto.setValue(value);
                auto.setDateBuilt(dateBuilt);
                auto.setUser(user.get());
                autoRepository.save(auto);
            }
        }

        return auto;
    }

    /**
     * Updates an existing auto object associated with a specific user.
     *
     * @param userId The unique identifier of the user who owns the auto
     * @param autoId The unique identifier of the auto to be updated
     * @param dateBuilt The new date when the auto was built
     * @param value The new monetary value of the auto
     * @return A string message indicating the result of the update operation
     */
    @PutMapping(path = RESTNouns.USER + RESTNouns.USER_ID + RESTNouns.AUTO + RESTNouns.AUTO_ID)
    public @ResponseBody String updateAutoByUser(
            @PathVariable("user_id") Long userId, @PathVariable("auto_id") Long autoId, @RequestParam LocalDate dateBuilt, @RequestParam int value){
        if (userRepository.existsById(userId) && autoRepository.existsById(autoId)) {
            Optional<OLD_User> user = userRepository.findById(userId);
            Optional<OLD_Auto> auto = autoRepository.findById(autoId);
            if(user.isPresent() && auto.isPresent()){
                auto.get().setDateBuilt(dateBuilt);
                auto.get().setValue(value);

                autoRepository.save(auto.get());
                userRepository.save(user.get());
            }
            return "Auto with ID " + autoId + " updated successfully.";
        } else {
            return "Auto with ID " + autoId + " not found.";
        }
    }

    /**
     * Deletes a specific auto object associated with a user.
     *
     * @param userId The unique identifier of the user who owns the auto
     * @param autoId The unique identifier of the auto to be deleted
     * @return A string message indicating the result of the deletion operation
     */
    @DeleteMapping(path = RESTNouns.USER + RESTNouns.USER_ID + RESTNouns.AUTO + RESTNouns.AUTO_ID)
    public @ResponseBody String deleteAutoByUser(
            @PathVariable("user_id") Long userId, @PathVariable("auto_id") Long autoId) {
        if (userRepository.existsById(userId) && autoRepository.existsById(autoId)) {
            autoRepository.deleteById(autoId);
            return "Auto with ID " + autoId + " deleted successfully.";
        } else {
            return "Auto with ID " + autoId + " not found.";
        }
    }
}