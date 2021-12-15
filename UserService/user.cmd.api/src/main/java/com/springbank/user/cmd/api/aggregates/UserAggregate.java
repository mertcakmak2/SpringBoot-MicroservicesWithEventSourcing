package com.springbank.user.cmd.api.aggregates;

import com.springbank.user.cmd.api.commands.RegisterUserCommand;
import com.springbank.user.cmd.api.commands.RemoveUserCommand;
import com.springbank.user.cmd.api.commands.UpdateUserCommand;
import com.springbank.user.cmd.api.security.PasswordEncoder;
import com.springbank.user.core.events.UserRegisterEvent;
import com.springbank.user.core.events.UserRemovedEvent;
import com.springbank.user.core.events.UserUpdatedEvent;
import com.springbank.user.core.models.User;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Aggregate
public class UserAggregate {

    @AggregateIdentifier
    private String id;
    private User user;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAggregate(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @CommandHandler
    public UserAggregate(RegisterUserCommand command, PasswordEncoder passwordEncoder) {
        var newUser = command.getUser();
        newUser.setId(command.getId());

        this.passwordEncoder = passwordEncoder;
        var password = newUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        newUser.getAccount().setPassword(hashedPassword);

        var event = UserRegisterEvent.builder()
                .id(command.getId())
                .user(newUser).build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(UpdateUserCommand command) {
        var updateUser = command.getUser();
        updateUser.setId(command.getId());
        var password = updateUser.getAccount().getPassword();
        var hashedPassword = passwordEncoder.hashPassword(password);
        updateUser.getAccount().setPassword(hashedPassword);

        var event = UserUpdatedEvent.builder()
                .id(UUID.randomUUID().toString())
                .user(updateUser).build();

        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(RemoveUserCommand command) {
        var event = new UserRemovedEvent();
        event.setId(command.getId());

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UserRegisterEvent event) {
        this.id = event.getId();
        this.user = event.getUser();
    }

    @EventSourcingHandler
    public void on(UserUpdatedEvent event) {
        this.user = event.getUser();

    }

    @EventSourcingHandler
    public void on(UserRemovedEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
