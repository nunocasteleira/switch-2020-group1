import React from "react";
import { DataGrid } from "@material-ui/data-grid";

const columns = [
  {
    field: "date",
    headerName: "Date",
    width: 150,
  },
  {
    field: "description",
    headerName: "Description",
    width: 250,
  },
  {
    field: "amount",
    headerName: "Amount",
    type: "number",
    width: 150,
  },
  {
    field: "category",
    headerName: "Category",
    width: 200,
  },
];

const rows = [
  {
    id: 20,
    date: "19/06/2021 12:54",
    description: "Electricity bill",
    amount: "-20.0",
    category: "G1 TRAVEL",
  },
  {
    id: 24,
    date: "01/01/2021 00:00",
    description: "Electricity bill",
    amount: "-20.0",
    category: "G1 TRAVEL",
  },
  {
    id: 22,
    date: "19/06/2021 12:54",
    description: "Electricity bill",
    amount: "-20.0",
    category: "G1 TRAVEL",
  },
];

const TransactionsTable = ({ transactions }) => {
  const tableRows = transactions.map((transaction) => ({
    id: transaction.transactionId,
    category: transaction.categoryName,
    date: transaction.date,
    description: transaction.description,
    amount: transaction.currency + " " + Number(transaction.amount) * -1,
  }));
  return (
    <div>
      {transactions && (
        <DataGrid
          rows={tableRows}
          columns={columns}
          pageSize={10}
          autoHeight={true}
          checkboxSelection={false}
          disableSelectionOnClick
        />
      )}
    </div>
  );
};

export default TransactionsTable;
