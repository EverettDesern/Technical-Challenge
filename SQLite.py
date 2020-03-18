import sqlite3
db = sqlite3.connect("moviri.sqlite.db")
cursor = db.cursor()

answer1 = """SELECT E.Employeeid, Sum(I.Total) FROM employees E, customers C, invoices I
                WHERE E.Employeeid = C.SupportRepid
                AND (C.Customerid = I.Customerid)
                GROUP BY E.Employeeid
                ORDER BY Sum(I.Total) DESC"""

answer2 = """SELECT employees.Employeeid FROM employees
                  LEFT JOIN customers
                  ON employees.Employeeid = customers.SupportRepid
                  WHERE customers.SupportRepid IS NULL"""

cursor.execute(answer1)

print("EmployeeId    total")
for row in cursor:
    print(row[0], "           ", row[1])
    
cursor.execute(answer2)

for row in cursor:
    print(row[0], "           ", "NULL")

db.close()
