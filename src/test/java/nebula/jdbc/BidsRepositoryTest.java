package nebula.jdbc;

import org.junit.jupiter.api.Test;

public class BidsRepositoryTest {

//	private static final Logger log = LoggerFactory.getLogger(BidsRepositoryTest.class);

	@Test
	public void shouldSelectEntityForMatchingIds() {

//		int userId = 1012;
//		int listingId = 2;

//		Database db = Database.builder().connectionProvider(connectionProvider).build();
//		db.update("create table Bids\n" + "(\n" + "\tID int not null,\n" + "\tUserID int not null\n"
//				+ "\tAmount money not null,\n" + "\tParticipation money default 0 not null,\n"
//				+ "\tMinRate decimal(6,3) not null,\n" + "\tListingID int not null\n"
//				+ "\tStandingBidID int default (-1) not null,\n" + "\tFundingAccountID int not null\n"
//				+ "\tCreationDate datetime default getdate() not null,\n"
//				+ "\tModifiedDate datetime default getdate() not null,\n" + "\tCollectionAgencyID int\n"
//				+ "\tWithdrawn bit default 0 not null,\n" + "\tAnonymous bit default 0 not null,\n"
//				+ "\tAltKey varchar(30) default substring(convert(varchar50,newid()),1,4) + convert(varchar50,(convert(bigint,getdate()) * 86400000 + datepart(hour,getdate()) * 3600000 + datepart(minute,getdate()) * 60000 + datepart(second,getdate()) * 1000 + datepart(millisecond,getdate()) + abs(checksum(newid())))) + substring(convert(varchar50,newid()),30,6) not null,\n"
//				+ "\tAltKeySearchID as checksum([AltKey]),\n" + "\tBidSourceID tinyint not null\n"
//				+ "\tMinYield decimal(6,3) not null,\n" + "\tInvestmentOrderID int\n" + "\tconstraint PK_Bids\n"
//				+ "\t\tprimary key (ID, ListingID)\n" + ") go");
//
//		db.update("INSERT INTO Bids (ID, UserId, Amount, Participation, MinRate, ListingId, BidSourceId, MinYield) "
//				+ " VALUES(1, 1012, 10000, 3000, 0.06, 2, 2, 12000) go");
//
//		bidsRepository.getBid(userId, listingId).forEach(a -> {
//			assertThat(a.getUserId()).isEqualTo(userId);
//			assertThat(a.getListingId()).isEqualTo(listingId);
//		});
	}
}