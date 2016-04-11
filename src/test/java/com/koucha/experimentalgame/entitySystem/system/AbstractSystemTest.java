package com.koucha.experimentalgame.entitySystem.system;

import com.koucha.experimentalgame.entitySystem.ChangeType;
import com.koucha.experimentalgame.entitySystem.Entity;
import com.koucha.experimentalgame.entitySystem.EntityManager;
import com.koucha.experimentalgame.entitySystem.EntityManager.ChangeProcessor;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class AbstractSystemTest
{
	// Only for readibility
	private static final boolean isAccepted = true;
	private static final boolean isInList = true;
	private static final boolean shouldBeAdded = true;
	private static final boolean shouldBeRemoved = true;
	private static final boolean testAdd = true;
	private static final boolean testRemove = true;
	private static final boolean isNOTAccepted = false;
	private static final boolean isNOTInList = false;
	private static final boolean shouldNOTBeAdded = false;
	private static final boolean shouldNOTBeRemoved = false;
	@SuppressWarnings( "unused" )
	private static final boolean DONTTestAdd = false;
	private static final boolean DONTTestRemove = false;
	private static final boolean dontCare = false;


	private static Entity entity;
	private static AbstractSystem sys;

	@BeforeClass
	public static void setUpMocking() throws Exception
	{
		entity = mock( Entity.class );

		sys = spy( AbstractSystem.class );

		//noinspection unchecked
		sys.entityList = mock( EntityList.class );

		sys.manager = mock( EntityManager.class );
	}

	@Test
	public void setManager() throws Exception
	{
		EntityManager manager = mock( EntityManager.class );
		when( manager.isSystemLinked( any() ) ).thenReturn( false );

		AbstractSystem sys = spy( AbstractSystem.class );
		when( sys.getFlag() ).thenReturn( null );

		sys.setEntityManager( manager );

		verify( manager ).isSystemLinked( any() );
		verify( manager ).link( any() );

		assertTrue( "private member 'manager' was not properly set", manager == sys.manager );
	}

	@Test
	public void updateEntityList_Add_ShouldAddAcceptedEntitiesNotInList() throws Exception
	{
		updateEntityList( ChangeType.Add, isAccepted, isNOTInList, shouldBeAdded, shouldNOTBeRemoved );
	}

	private void updateEntityList( ChangeType changeType, boolean isAccepted, boolean isInList, boolean shouldBeAdded, boolean shouldBeRemoved )
	{
		updateEntityList( changeType, isAccepted, isInList, shouldBeAdded, shouldBeRemoved, testAdd, testRemove );
	}

	private void updateEntityList( ChangeType changeType, boolean isAccepted, boolean isInList, boolean shouldBeAdded, boolean shouldBeRemoved, boolean testAdd, boolean testRemove )
	{
		//noinspection unchecked
		reset( sys.entityList );

		when( sys.acceptEntity( entity ) ).thenReturn( isAccepted );

		when( sys.entityList.contains( entity ) ).thenReturn( isInList );

		doAnswer( (Answer< Void >) invocation -> {
			Object[] args = invocation.getArguments();
			ChangeProcessor proc = (ChangeProcessor) args[1];

			proc.process( entity, changeType );
			if( testAdd )
			{
				verify( sys.entityList, times( (shouldBeAdded) ? (1) : (0) ) ).add( any() );
			}
			if( testRemove )
			{
				//noinspection SuspiciousMethodCalls
				verify( sys.entityList, times( (shouldBeRemoved) ? (1) : (0) ) ).remove( any() );
			}

			return null;
		} ).when( sys.manager ).checkChangedEntities( any(), any( ChangeProcessor.class ) );

		//do the test:
		sys.updateEntityList();
	}

	@Test
	public void updateEntityList_Add_ShouldIgnoreAcceptedEntitiesInList() throws Exception
	{
		updateEntityList( ChangeType.Add, isAccepted, isInList, shouldNOTBeAdded, shouldNOTBeRemoved );
	}

	@Test
	public void updateEntityList_Add_ShouldIgnoreNoTAcceptedEntitiesNotInList() throws Exception
	{
		updateEntityList( ChangeType.Add, isNOTAccepted, isNOTInList, shouldNOTBeAdded, shouldNOTBeRemoved );
	}

	@Test
	public void updateEntityList_Add_ShouldIgnoreNotAcceptedEntitiesInList() throws Exception
	{
		updateEntityList( ChangeType.Add, isNOTAccepted, isInList, shouldNOTBeAdded, shouldNOTBeRemoved );
	}

	@Test
	public void updateEntityList_Addition_ShouldAddAcceptedEntitiesNotInList() throws Exception
	{
		updateEntityList( ChangeType.Addition, isAccepted, isNOTInList, shouldBeAdded, shouldNOTBeRemoved );
	}

	@Test
	public void updateEntityList_Addition_ShouldIgnoreAcceptedEntitiesInList() throws Exception
	{
		updateEntityList( ChangeType.Addition, isAccepted, isInList, shouldNOTBeAdded, shouldNOTBeRemoved );
	}

	@Test
	public void updateEntityList_Addition_ShouldIgnoreNoTAcceptedEntitiesNotInList() throws Exception
	{
		updateEntityList( ChangeType.Addition, isNOTAccepted, isNOTInList, shouldNOTBeAdded, shouldNOTBeRemoved );
	}

	@Test
	public void updateEntityList_Addition_ShouldIgnoreNotAcceptedEntitiesInList() throws Exception
	{
		updateEntityList( ChangeType.Addition, isNOTAccepted, isInList, shouldNOTBeAdded, shouldNOTBeRemoved );
	}

	@Test
	public void updateEntityList_Deletion_ShouldIgnoreAcceptedEntitiesNotInList() throws Exception
	{
		updateEntityList( ChangeType.Deletion, isAccepted, isNOTInList, shouldNOTBeAdded, shouldNOTBeRemoved );
	}

	@Test
	public void updateEntityList_Deletion_ShouldIgnoreAcceptedEntitiesInList() throws Exception
	{
		updateEntityList( ChangeType.Deletion, isAccepted, isInList, shouldNOTBeAdded, shouldNOTBeRemoved );
	}

	@Test
	public void updateEntityList_Deletion_ShouldNotAddNoTAcceptedEntitiesNotInList() throws Exception
	{
		updateEntityList( ChangeType.Deletion, isNOTAccepted, isNOTInList, shouldNOTBeAdded, dontCare, testAdd, DONTTestRemove );
	}

	@Test
	public void updateEntityList_Deletion_ShouldRemoveNotAcceptedEntitiesInList() throws Exception
	{
		updateEntityList( ChangeType.Deletion, isNOTAccepted, isInList, shouldNOTBeAdded, shouldBeRemoved );
	}

	@Test
	public void updateEntityList_Remove_ShouldNotAddAcceptedEntitiesNotInList() throws Exception
	{
		updateEntityList( ChangeType.Remove, isAccepted, isNOTInList, shouldNOTBeAdded, dontCare, testAdd, DONTTestRemove );
	}

	@Test
	public void updateEntityList_Remove_ShouldRemoveAcceptedEntitiesInList() throws Exception
	{
		updateEntityList( ChangeType.Remove, isAccepted, isInList, shouldNOTBeAdded, shouldBeRemoved );
	}

	@Test
	public void updateEntityList_Remove_ShouldNotAddNoTAcceptedEntitiesNotInList() throws Exception
	{
		updateEntityList( ChangeType.Remove, isNOTAccepted, isNOTInList, shouldNOTBeAdded, dontCare, testAdd, DONTTestRemove );
	}

	@Test
	public void updateEntityList_Remove_ShouldRemoveNotAcceptedEntitiesInList() throws Exception
	{
		updateEntityList( ChangeType.Remove, isNOTAccepted, isInList, shouldNOTBeAdded, shouldBeRemoved );
	}
}