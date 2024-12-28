package com.example.belejki.demo.datainit;

import com.example.belejki.demo.entity.Authority;
import com.example.belejki.demo.entity.Reminder;
import com.example.belejki.demo.entity.User;
import com.example.belejki.demo.repository.AuthorityRepository;
import com.example.belejki.demo.repository.ReminderRepository;
import com.example.belejki.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, ReminderRepository reminderRepository, AuthorityRepository authorityRepository) {
        return args -> {

            //user Venio
            Authority roleAdmin = new Authority("ROLE_ADMIN", null);
            Authority roleUser = new Authority("ROLE_USER", null);
            User venio = new User("venio@abv.bg",1,"Venislav","Stoyanov", LocalDate.now(),"{noop}1q2w3e",false, List.of(roleAdmin, roleUser));
            roleUser.setUser(venio);
            roleAdmin.setUser(venio);

            /* venio reminders */
            Reminder vinetka = new Reminder("kola: vinetka","", LocalDate.of(2025, 4, 25),false,false,9, venio,false);
            Reminder masloq = new Reminder("kola: smqna na maslo","ili 85000km", LocalDate.of(2025, 8, 15),false,false,5, venio,false);
            Reminder rojdenDen = new Reminder("Rojden den Nono","", LocalDate.of(2025, 9, 8),false,false,9, venio,false);
            Reminder pregled = new Reminder("kola: pregled","neobhodimi: golqm talon, platena grajdanska", LocalDate.of(2025, 8, 18),false,false,8,venio,false);
            Reminder danuk = new Reminder("kola: danuk","", LocalDate.of(2025, 7, 15),false,false,6, venio,false);
            List<Reminder> venioReminders = List.of(vinetka,masloq,rojdenDen,pregled,danuk);
            venio.setReminders(venioReminders);


            //user vili
            Authority viliUser = new Authority("ROLE_USER", null);
            User vili = new User("vili@abv.bg",1,"Violeta","Stoyanova", LocalDate.now(),"{noop}1q2w3e",false, List.of(viliUser));
            viliUser.setUser(vili);

            //user pesho
            Authority peshoUser = new Authority("ROLE_USER", null);
            User pesho = new User("pesho@abv.bg",1,"Pesho","Peshev", LocalDate.now(),"{noop}1q2w3e",false,List.of(peshoUser));
            peshoUser.setUser(pesho);
            Authority hristoUser = new Authority("ROLE_USER", null);
            User hristo = new User("hristo@abv.bg",1,"Hristo","Hristev", LocalDate.now(),"{noop}1q2w3e",false,List.of(hristoUser));
            hristoUser.setUser(hristo);

            //combine users
            List<User> users = List.of(venio, vili, pesho, hristo);

            //save users
            userRepository.saveAll(users);

            System.out.println("Dummy data inserted!");
        };
    }
}
