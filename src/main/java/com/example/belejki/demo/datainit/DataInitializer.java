package com.example.belejki.demo.datainit;

import com.example.belejki.demo.entity.*;
import com.example.belejki.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            UserRepository userRepository,
            IngredientRepository ingredientRepository,
            RecipeRepository recipeRepository
    ) {
        return args -> {

            //user Venio
            Authority roleAdmin = new Authority("ROLE_ADMIN", null);
            Authority roleUser = new Authority("ROLE_USER", null);
            User venio = new User("venio@abv.bg","Venislav","Stoyanov","{noop}1q2w3e");
            venio.setAuthorities(List.of(roleUser, roleAdmin));
            roleUser.setUser(venio);
            roleAdmin.setUser(venio);

            /* venio reminders */
            Reminder vinetka = new Reminder("kola: vinetka","", LocalDate.of(2025, 4, 25),9);
            Reminder masloq = new Reminder("kola: smqna na maslo","ili 85000km", LocalDate.of(2025, 8, 15),5);
            Reminder rojdenDen = new Reminder("Rojden den Nono","", LocalDate.of(2025, 9, 8),9);
            Reminder pregled = new Reminder("kola: pregled","neobhodimi: golqm talon, platena grajdanska", LocalDate.of(2025, 8, 18),8);
            Reminder danuk = new Reminder("kola: danuk","", LocalDate.of(2025, 7, 15),6);
            List<Reminder> venioReminders = List.of(vinetka,masloq,rojdenDen,pregled,danuk);
            venioReminders.forEach(reminder -> reminder.setUser(venio));
            venio.setReminders(venioReminders);


            //user vili
            Authority viliUser = new Authority("ROLE_USER", null);
            User vili = new User("vili@abv.bg","Violeta","Stoyanova","{noop}1q2w3e");
            vili.setAuthorities(List.of(viliUser));
            viliUser.setUser(vili);

            //user pesho
            Authority peshoUser = new Authority("ROLE_USER", null);
            User pesho = new User("pesho@abv.bg","Pesho","Peshev","{noop}1q2w3e");
            pesho.setAuthorities(List.of(peshoUser));
            peshoUser.setUser(pesho);
            Authority hristoUser = new Authority("ROLE_USER", null);
            User hristo = new User("hristo@abv.bg","Hristo","Hristev","{noop}1q2w3e");
            hristo.setAuthorities(List.of(hristoUser));
            hristoUser.setUser(hristo);

            Ingredient chushki = Ingredient.builder().name("chushki").quantity("1kg").build();
            Ingredient kaima = Ingredient.builder().name("kaima").quantity("500gr").build();
            Recipe pulneniChushki = Recipe.builder()
                    .name("pulneni chushki")
                    .ingredients(List.of(chushki, kaima))
                    .user(venio).build();
            chushki.setRecipe(pulneniChushki);
            kaima.setRecipe(pulneniChushki);
            pulneniChushki.setUser(venio);
            venio.getRecipes().add(pulneniChushki);

            //combine users
            List<User> users = List.of(venio, vili, pesho, hristo);

            //save users
            userRepository.saveAll(users);

            venio.getFriendships().add(new Friendship(null, venio, vili));
            userRepository.save(venio);

            System.out.println("Dummy data inserted!");
        };
    }
}
