create trigger trg_increment_qty_answers_topic after insert on answers
for each row
begin    
    update topics
    set answers = answers + 1
    where id = new.topic_id;
end;

create trigger trg_decrease_qty_answers_topic after delete on answers
for each row
begin    
    update topics
    set answers = answers - 1
    where id = old.topic_id;
end;