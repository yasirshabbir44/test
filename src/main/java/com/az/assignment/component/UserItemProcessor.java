package com.az.assignment.component;

import com.az.assignment.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class UserItemProcessor implements ItemProcessor<User, User> {

    // I make this simple by assuming that input data object and required data object are same parameters
    // In many cases input file objects are different then output ItemProcessor<UserInput, UserOutput>

    @Override
    public User process(User item) throws Exception {
       // here we can do any adaptation if required.
        final String firstName = item.getFirstName().toUpperCase();
        final String lastName = item.getLastName().toUpperCase();
        final User transformedUser = new User(firstName,lastName, item.getAge());
        log.debug("Adaptation User: item{}, user{}", item, transformedUser);
        return transformedUser;
    }
}
