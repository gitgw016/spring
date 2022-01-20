package net.koreate.lombok;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;

//@Data
//@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of= {"uid","upw"}, callSuper=false)
@Builder
public class UserVO {

	private int uno;
	@Getter @Setter @NonNull
	private String uid;
	@Setter @NonNull
	private String upw;
	private String uname;
	private Date regdate;
	@Singular("list") @Getter
	private List<String> friendList;
}
