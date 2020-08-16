package br.com.lsukys.centraldeerros.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.lsukys.centraldeerros.dto.UserDTO;
import br.com.lsukys.centraldeerros.entity.User;


@Mapper(componentModel = "spring", uses = {})
public interface UserMapper {

	UserDTO userToUserDTO(User entity);
	
	User userDTOToUser(UserDTO dto);

	List<UserDTO> userListToUserDTOList(List<User> list);

	List<User> userDTOListToUserList(List<UserDTO> list);
}
