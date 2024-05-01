package com.hangeulbada.domain.group.repository;

import com.hangeulbada.domain.group.dto.SubmitDTO;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {
    @Aggregation(pipeline = {
            "{ $match: { '_id': ?0 } }",
            "{ $lookup: { from: 'User', localField: 'studentIds', foreignField: '_id', as: 'Student' } }",
            "{ $unwind: '$Student' }",
            "{ $lookup: { from: 'Assignment', localField: 'Student._id', foreignField: 'studentId', as: 'Assignment' } }",
            "{ $unwind: '$Assignment' }",
            "{ $lookup: { from: 'Workbook', localField: 'Assignment.workbookId', foreignField: '_id', as: 'Workbook' } }",
            "{ $unwind: '$Workbook' }",
            "{ $project: { _id: 0, groupName: 1, name: '$Student.name', workbookId: '$Workbook._id',workbookTitle: '$Workbook.title',assignmentId: '$Assignment._id', score: '$Assignment.score',  submitDate:  '$Assignment.submitDate'} }",
            "{ $sort: { submitDate: 1 } }",
    })
    List<SubmitDTO> getRecentSubmit(String groupId);
}
