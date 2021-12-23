package org.example.booklibrary.core.reader;

import javassist.NotFoundException;
import org.example.booklibrary.core.reader.web.ReaderRequest;
import org.example.booklibrary.core.reader.web.ReaderView;
import org.example.booklibrary.util.DateTimeUtility;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderService {
    private final ReaderRepository readerRepo;

    public ReaderService(ReaderRepository readerRepo) {
        this.readerRepo = readerRepo;
    }

    private ReaderView convertToView(Reader reader) {
        ReaderView view = new ReaderView();
        view.setAddress(reader.getAddress());
        view.setName(reader.getName());
        view.setAge(reader.getAge());
        view.setId(reader.getId());
        view.setCreatedDate(reader.getCreatedDate());
        view.setUpdatedDate(reader.getUpdatedDate());
        return view;
    }
    public ReaderView getReaderById(Long id) throws NotFoundException {
        Reader reader;
        reader = readerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("No Reader Found For given Id"));
        return convertToView(reader);
    }

    public Page<ReaderView> getAllReaders(Pageable pageable) {
        Page<Reader> readers = readerRepo.findAll(pageable);
        List<ReaderView> readerViews = new ArrayList<>();
        readers.forEach(reader -> {
            ReaderView readerView = convertToView(reader);
            readerViews.add(readerView);
        });
        return new PageImpl<>(readerViews, pageable, readers.getTotalElements());
    }

    public ReaderView create(ReaderRequest req) {
        Reader reader = new Reader();
        reader.setAddress(req.getAddress());
        reader.setName(req.getName());
        reader.setAge(req.getAge());
        reader.setCreatedDate(DateTimeUtility.getCurrentUTCDateTime());
        reader.setUpdatedDate(DateTimeUtility.getCurrentUTCDateTime());
        Reader createdReader = readerRepo.save(reader);
        return convertToView(createdReader);
    }

    public ReaderView update(Long id, ReaderRequest req) throws NotFoundException {
        Reader readerToUpdate = readerRepo.findById(id).orElseThrow(() -> new NotFoundException("No Reader Found For given Id"));
        readerToUpdate.setAddress(req.getAddress());
        readerToUpdate.setName(req.getName());
        readerToUpdate.setAge(req.getAge());
        readerToUpdate.setUpdatedDate(DateTimeUtility.getCurrentUTCDateTime());
        Reader updatedReader = readerRepo.save(readerToUpdate);
        return convertToView(updatedReader);
    }

    public Void remove(Long id) throws NotFoundException {
        try {
            readerRepo.deleteById(id);
        } catch(EmptyResultDataAccessException e) {
            throw new NotFoundException(e.getMessage());
        }
        return null;
    }
}
