package com.example.sweater.repos;

import com.example.sweater.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//Этот интерфейс нужен чтобы добавить к нашей сущности Message crud операции и создать репозиторий который в данном контенксте
// является как раз таки прослойкой между бд и языком, позволяющей не использовать напрямую sql запросы
public interface MessageRepo extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
