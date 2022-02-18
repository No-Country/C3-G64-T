package com.getpass.Getpass.servicios;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.getpass.Getpass.entidades.EventImage;
import com.getpass.Getpass.excepciones.ErrorService;
import com.getpass.Getpass.repositorio.EventImageRepository;

@Service
public class EventImageService {
	
	@Autowired
	EventImageRepository eventImageRepository;
	
	@Transactional
	public EventImage uploadPhoto(String photoId,MultipartFile file) throws ErrorService{
		if (file != null) {
			
			//lanza error al querer subir imagenes con un peso mayor a 2MB
			if(file.getSize()>(1024*1024*2)) {
				throw new ErrorService("Tamaño de imagen excedido.");
			}
			try {
				EventImage image;
				if(photoId!=null) {
					image=photoByID(photoId);
				}else {
					image = new EventImage();
				}
				
				image.setMime(file.getContentType());
				image.setName(file.getName());
				image.setContent(file.getBytes());

				return eventImageRepository.save(image);
			} catch(ErrorService es) {
				System.err.println(es.getMessage());
			}
			catch (Exception e) {
				System.err.println(e.getMessage());
				throw new ErrorService(e.getMessage());
			}
		}
		return null;
	}
	
	@Transactional(readOnly = true)
	public EventImage photoByID(String id) throws ErrorService{
		Optional<EventImage> result = eventImageRepository.findById(id);
		
		if(result.isPresent()) {
			return result.get();
		}else {
			throw new ErrorService("No se encontro foto de evento con ese id");
		}
	}
	
	@Transactional
	public void deletePhoto(String id) throws ErrorService {
		Optional<EventImage> result = eventImageRepository.findById(id);

		if (result.isPresent()) {
			EventImage image = result.get();

			eventImageRepository.delete(image);
		} else {
			throw new ErrorService("no se pudo eliminar la imagen ya que no se pudo encontrar");
		}
	}

}
