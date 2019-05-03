use watchrecords
go
set ansi_nulls, quoted_identifier on;
go
-- select * from ebaysearchresults;
-- drop table ebaysearchresults;
-- truncate table ebaysearchresults;

-- select * from sys.foreign_keys fk where fk.referenced_object_id = object_id(N'ebaysearchresults');
-- script these as DROP-AND-CREATE-TO and copy-pasta here
go

if object_id(N'ebaysearchresults','U') is not null
begin
	set nocount on;
	
-- set this to a local table instead of a #temp_table for post-deploy audit
	select * 
	into #dropNpop_ebaysearchresults
	from ebaysearchresults;

	drop table ebaysearchresults;
-- drop table if exists N'ebaysearchresults';
	print 'dropped table ebaysearchresults SUCCESSFULLY! At time ' + convert(varchar,getdate(),126);
end;
go

create table ebaysearchresults (
	constraint pk_ebaysearchresults_Id primary key ( ID ),
	constraint ak_ebaysearchresults_Name unique ( [Name] ),
	ID int not null identity,
	[Name] varchar( 100 ) not null constraint chk_ebaysearchresults_NameIsNotEmpty check (datalength(ltrim(rtrim([Name])))>0),
	
	InsertBy varchar( 100 ) not null constraint df_ebaysearchresults_InsertBy default system_user,
	InsertDT datetime not null constraint df_ebaysearchresults_InsertDT default getdate(),
	UpdateBy varchar( 100 ) not null constraint df_ebaysearchresults_UpdateBy default system_user,
	UpdateDT datetime not null constraint df_ebaysearchresults_UpdateDT default getdate(),
	Revision int not null constraint df_ebaysearchresults_Revision default 0
);
go

-- The below may function improperly if column names/datatypes/other metadata is altered
-- It functions best for column addition or deletion from source code
if object_id( N'tempdb..#dropNpop_ebaysearchresults','U' ) is not null
begin
	<HasId,default "no",-- >set identity_insert ebaysearchresults on;

	declare @sql nvarchar(max) = ''; 

	select @sql += 
		quotename( c1.[name] ) + ',' 
	from sys.columns c1
	join tempdb.sys.columns c2 on 
		c2.[name] = c1.[name] and
		c2.[object_id] = object_id(N'tempdb..#dropNpop_ebaysearchresults')
	where c1.[object_id] = object_id(N'ebaysearchresults') and 
		c1.is_computed = 0;

	set @sql = left( @sql, len( @sql ) - 1 ); -- trim last comma
		
	set @sql = 
	'insert ebaysearchresults 
		(' + @sql + ') 
	select 
		' + @sql + '
	from #dropNpop_ebaysearchresults;';

	--print @sql;
	exec sp_executesql @sql;

	print convert(varchar,@@rowcount) + ' row(s) loaded into [].[ebaysearchresults].';

	<HasId,default "no",-- > set identity_insert ebaysearchresults off;
end;

go

if object_id(N'ebaysearchresults','U') is not null
	print 'created table ebaysearchresults SUCCESSFULLY! At time ' + convert(varchar,getdate(),126);
go