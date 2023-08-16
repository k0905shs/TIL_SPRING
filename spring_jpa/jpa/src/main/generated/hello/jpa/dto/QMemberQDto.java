package hello.jpa.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * hello.jpa.dto.QMemberQDto is a Querydsl Projection type for MemberQDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberQDto extends ConstructorExpression<MemberQDto> {

    private static final long serialVersionUID = -212193786L;

    public QMemberQDto(com.querydsl.core.types.Expression<String> userName, com.querydsl.core.types.Expression<Integer> age) {
        super(MemberQDto.class, new Class<?>[]{String.class, int.class}, userName, age);
    }

}

