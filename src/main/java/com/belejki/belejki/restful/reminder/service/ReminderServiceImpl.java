package com.belejki.belejki.restful.reminder.service;

import com.belejki.belejki.restful.reminder.web.dto.ReminderPatchDto;
import com.belejki.belejki.restful.reminder.web.dto.ReminderDto;
import com.belejki.belejki.restful.scheduler.web.dto.ReminderSchedulerDto;
import com.belejki.belejki.restful.reminder.domain.Reminder;
import com.belejki.belejki.restful.shared.exception.RecipeNotFoundException;
import com.belejki.belejki.restful.user.domain.User;
import com.belejki.belejki.restful.shared.exception.ReminderNotFoundException;
import com.belejki.belejki.restful.shared.exception.user.UserNotFoundException;
import com.belejki.belejki.restful.reminder.repository.ReminderRepository;
import com.belejki.belejki.restful.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReminderServiceImpl implements ReminderService {
	private static final int DAYS_BEFORE_EXPIRE = 7;
	private static final int YEARS_AFTER_EXPIRED = 2;
	private final ReminderRepository reminderRepository;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	public ReminderServiceImpl(ReminderRepository reminderRepository, UserRepository userRepository, ModelMapper modelMapper) {
		this.reminderRepository = reminderRepository;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}


	public ReminderDto save(@Valid ReminderDto reminder, String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
		Reminder entity = modelMapper.map(reminder, Reminder.class);
		setExpirationFlags(entity);
		entity.setUser(user);
		Reminder saved = reminderRepository.save(entity);

		return modelMapper.map(saved, ReminderDto.class);
	}

	@Override
	public ReminderDto updateByIdAndUser_Username(ReminderDto dto, String username) {

		Reminder byId = reminderRepository.findByIdAndUser_UsernameOrderByExpirationAsc(dto.getId(), username)
				.orElseThrow(() -> new RecipeNotFoundException("Reminder not found for id: " + dto.getId()));

		Reminder updated = this.update(byId, dto);
		return modelMapper.map(updated, ReminderDto.class);
	}

	@Override
	public ReminderDto patchReminderByIdAndUser_Username(ReminderPatchDto dto, String username) {

		Reminder reminder = reminderRepository.findByIdAndUser_UsernameOrderByExpirationAsc(dto.getId(), username)
				.orElseThrow(() -> new ReminderNotFoundException("Reminder not found for id: " + dto.getId()));

		Reminder patched = this.patchReminder(reminder, dto);
		return modelMapper.map(patched, ReminderDto.class);
	}

	public ReminderDto findById(Long id) {
		Reminder reminder = reminderRepository.findById(id).orElseThrow(() -> new ReminderNotFoundException("Reminder not found for id: " + id));
		return modelMapper.map(reminder, ReminderDto.class);
	}

	@Override
	public ReminderDto findByIdAndUser_UsernameOrderByExpirationAsc(Long id, String username) {
		Reminder byIdAndUserUsernameOrderByExpirationAsc = reminderRepository.findByIdAndUser_UsernameOrderByExpirationAsc(id, username)
				.orElseThrow(() -> new ReminderNotFoundException("Reminder not found."));
		return modelMapper.map(byIdAndUserUsernameOrderByExpirationAsc, ReminderDto.class);
	}

	@Override
	public Page<ReminderDto> findAll(Pageable pageable) {
		Page<Reminder> all = reminderRepository.findAll(pageable);
		return all.map(reminder -> modelMapper.map(reminder, ReminderDto.class));
	}

	@Override
	public Page<ReminderDto> findAllByUser_IdOrderByExpirationAsc(Long userId, Pageable pageable) {
		Page<Reminder> allByUserIdOrderByExpirationAsc = reminderRepository.findAllByUser_IdOrderByExpirationAsc(userId, pageable);
		return allByUserIdOrderByExpirationAsc.map(reminder -> modelMapper.map(reminder, ReminderDto.class));
	}

	@Override
	public Page<ReminderDto> findAllByUser_UsernameOrderByExpirationAsc(String username, Pageable pageable) {
		Page<Reminder> allByUserUsernameOrderByExpirationAsc = reminderRepository.findAllByUser_UsernameOrderByExpirationAsc(username, pageable);
		return allByUserUsernameOrderByExpirationAsc.map(reminder -> modelMapper.map(reminder, ReminderDto.class));
	}

	@Override
	public Page<ReminderDto> findAllByExpiredTrueOrderByExpirationAsc(Pageable pageable) {
		Page<Reminder> allByExpiredTrueOrderByExpirationAsc = reminderRepository.findAllByExpiredTrueOrderByExpirationAsc(pageable);
		return allByExpiredTrueOrderByExpirationAsc.map(reminder -> modelMapper.map(reminder, ReminderDto.class));
	}

	public Page<ReminderDto> findAllByUser_Username(String username, Pageable pageable) {
		Page<Reminder> allByUserUsernameOrderByExpirationAsc = reminderRepository.findAllByUser_UsernameOrderByExpirationAsc(username, pageable);
		return allByUserUsernameOrderByExpirationAsc.map((element) -> modelMapper.map(element, ReminderDto.class));
	}

	public Page<ReminderDto> findAllByUser_Id(Long userId, Pageable pageable) {
		Page<Reminder> allByUserIdOrderByExpirationAsc = reminderRepository.findAllByUser_IdOrderByExpirationAsc(userId, pageable);
		return allByUserIdOrderByExpirationAsc.map((element) -> modelMapper.map(element, ReminderDto.class));
	}

	public Page<ReminderDto> findAllByExpiredFalseAndExpiresSoonFalseAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable) {
		Page<Reminder> allByExpiredFalseAndExpiresSoonFalseAndUserUsernameOrderByExpirationAsc = reminderRepository.findAllByExpiredFalseAndExpiresSoonFalseAndUser_UsernameOrderByExpirationAsc(username, pageable);
		return allByExpiredFalseAndExpiresSoonFalseAndUserUsernameOrderByExpirationAsc.map((element) -> modelMapper.map(element, ReminderDto.class));
	}

	public Page<ReminderDto> findAllByExpiredTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable) {
		Page<Reminder> allByExpiredTrueAndUserUsernameOrderByExpirationAsc = reminderRepository.findAllByExpiredTrueAndUser_UsernameOrderByExpirationAsc(username, pageable);
		return allByExpiredTrueAndUserUsernameOrderByExpirationAsc.map((element) -> modelMapper.map(element, ReminderDto.class));
	}

	public Page<ReminderDto> findAllByExpiresSoonTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable) {
		Page<Reminder> allByExpiresSoonTrueAndUserUsernameOrderByExpirationAsc = reminderRepository.findAllByExpiresSoonTrueAndUser_UsernameOrderByExpirationAsc(username, pageable);
		return allByExpiresSoonTrueAndUserUsernameOrderByExpirationAsc.map((element) -> modelMapper.map(element, ReminderDto.class));
	}

	public Page<ReminderDto> findAllByExpiresTodayTrueAndUser_UsernameOrderByExpirationAsc(String username, Pageable pageable) {
		Page<Reminder> allByExpiresTodayTrueAndUserUsernameOrderByExpirationAsc = reminderRepository.findAllByExpiresTodayTrueAndUser_UsernameOrderByExpirationAsc(username, pageable);
		return allByExpiresTodayTrueAndUserUsernameOrderByExpirationAsc.map((element) -> modelMapper.map(element, ReminderDto.class));
	}

	public Page<ReminderDto> findAllByNameContainingAndUser_UsernameOrderByExpirationAsc(String name, String username, Pageable pageable) {
		Page<Reminder> allByNameContainingAndUserUsernameOrderByExpirationAsc = reminderRepository.findAllByNameContainingAndUser_UsernameOrderByExpirationAsc(name, username, pageable);
		return allByNameContainingAndUserUsernameOrderByExpirationAsc
				.map((element) -> modelMapper.map(element, ReminderDto.class));
	}

	public Page<ReminderDto> findAllByDescriptionContainingAndUser_UsernameOrderByExpirationAsc(String descr, String username, Pageable pageable) {
		Page<Reminder> allByDescriptionContainingAndUserUsernameOrderByExpirationAsc = reminderRepository.findAllByDescriptionContainingAndUser_UsernameOrderByExpirationAsc(descr, username, pageable);
		return allByDescriptionContainingAndUserUsernameOrderByExpirationAsc
				.map((element) -> modelMapper.map(element, ReminderDto.class));
	}


	@Transactional
	@Override
	public void deleteById(Long id) {
		reminderRepository.deleteById(id);
	}

	@Transactional
	@Override
	public void deleteByIdAndUser_Username(Long id, String username) {
		reminderRepository.deleteByIdAndUser_Username(id, username);
	}

	@Transactional
	@Override
	public void deleteAllByUser_Id(Long id) {

		reminderRepository.deleteAllByUser_Id(id);
	}

	@Transactional
	@Override
	public void deleteAllExpiredBeforeYears(int years) {
		LocalDate beforeTwoYears = LocalDate.now().minusYears(years);
		reminderRepository.deleteAllByExpirationBefore(beforeTwoYears);

	}

	@Transactional
	public void deleteAllByUser_Username(String username) {
		reminderRepository.deleteAllByUser_Username(username);
	}

	public Reminder update(Reminder reminder, @Valid ReminderDto dto) {
		reminder.setName(dto.getName());
		reminder.setDescription(dto.getDescription());
		reminder.setImportanceLevel(dto.getImportanceLevel());
		reminder.setExpiration(dto.getExpiration());
		reminder.setExpired(dto.isExpired());
		reminder.setExpiresSoon(dto.isExpiresSoon());
		reminder.setExpiresToday(dto.isExpiresToday());
		setExpirationFlags(reminder);
		//resetting flags for the scheduler
		reminder.setMonthMail(false);
		reminder.setWeekMail(false);
		reminder.setTodayMail(false);

		return reminderRepository.save(reminder);
	}

	public Reminder patchReminder(Reminder reminder, ReminderPatchDto dto) {

		// Update only fields that are provided
		if (dto.getName() != null && !dto.getName().isBlank()) {
			reminder.setName(dto.getName());
		}

		if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
			reminder.setDescription(dto.getDescription());
		}

		if (dto.getImportanceLevel() != null &&
				dto.getImportanceLevel() > 0 &&
				dto.getImportanceLevel() < 11) {

			reminder.setImportanceLevel(dto.getImportanceLevel());
		}

		if (dto.getExpiration() != null && dto.getExpiration().isAfter(LocalDate.now())) {
			reminder.setExpiration(dto.getExpiration());
			setExpirationFlags(reminder);
			//resetting flags for the scheduler
			reminder.setMonthMail(false);
			reminder.setWeekMail(false);
			reminder.setTodayMail(false);
		}

		return reminderRepository.save(reminder);
	}

	public void patchScheduleReminder(ReminderSchedulerDto dto) {

		Optional<Reminder> reminder = reminderRepository.findById(dto.getId());

		if (reminder.isPresent()) {

			if (dto.getName() != null) {
				reminder.get().setName(dto.getName());
			}
			if (dto.getExpiration() != null) {
				reminder.get().setExpiration(dto.getExpiration());
				setExpirationFlags(reminder.get());
			}
			if (dto.getExpired() != null) {
				reminder.get().setExpired(dto.getExpired());
			}
			if (dto.getExpiresAfterMonth() != null) {
				reminder.get().setExpiresAfterMonth(dto.getExpiresAfterMonth());
			}
			if (dto.getExpiresSoon() != null) {
				reminder.get().setExpiresSoon(dto.getExpiresSoon());
			}
			if (dto.getExpiresToday() != null) {
				reminder.get().setExpiresToday(dto.getExpiresToday());
			}
			if (dto.getMonthMail() != null) {
				reminder.get().setMonthMail(dto.getMonthMail());
			}
			if (dto.getWeekMail() != null) {
				reminder.get().setWeekMail(dto.getWeekMail());
			}
			if (dto.getTodayMail() != null) {
				reminder.get().setTodayMail(dto.getTodayMail());
			}
			reminderRepository.save(reminder.get());
		}
	}

	private void setExpirationFlags(Reminder reminder) {
		LocalDate now = LocalDate.now();
		LocalDate reminderDate = reminder.getExpiration();
		boolean isLessThanDaysBeforeExpire = now.isAfter(reminderDate.minusDays(DAYS_BEFORE_EXPIRE + 1)); //adding 1 to make it before today
		boolean expired = now.isAfter(reminderDate);
		boolean expiresToday = now.isEqual(reminderDate);
		boolean expiresAfterMonth = now.isEqual(reminderDate.minusMonths(1));

		reminder.setExpired(expired);
		reminder.setExpiresSoon(isLessThanDaysBeforeExpire && !expired);
		reminder.setExpiresToday(expiresToday);
		reminder.setExpiresAfterMonth(expiresAfterMonth);
	}

}
