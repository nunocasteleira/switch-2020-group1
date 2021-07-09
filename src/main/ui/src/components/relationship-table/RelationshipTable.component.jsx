import React, {useContext} from "react";
import "../create-relationship/CreateRelationship.styles.css";

import AppContext from "../../context/AppContext";

import {StyledTableCell, StyledTableRow, useStyles} from './RelationshipTable.styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import {useHistory} from 'react-router-dom'

export default function RelationshipsTable(props) {
    const { state } = useContext(AppContext);
    const { familyId } = state;  

    const classes = useStyles();
    const history = useHistory();

    let handleClickCreate = () => {
        history.push(`/families/${familyId}/relationships/create`);
    }

    let handleClickEdit = (relationshipId) => {
        history.push(`/families/${familyId}/relationships/${relationshipId}`);
    }

    if (props.relationships.relationshipList.length === 0) {
        return (
            <div align='center'>
                <h1>Relationships</h1>
                <p>There are no relationships in this family.</p>
                <br/>
                <button onClick={handleClickCreate} id="button"> Create</button>
            </div>
        )
    }

    return (
        <div align='center'>
            <h1>Relationships</h1>
            <br/>
            <TableContainer component={Paper}>
                <Table className={classes.table} aria-label="customized table">
                    <TableHead>
                        <TableRow>
                            <StyledTableCell align="center">User</StyledTableCell>
                            <StyledTableCell align="center"> </StyledTableCell>
                            <StyledTableCell align="center">Relationship Type</StyledTableCell>
                            <StyledTableCell align="center"> </StyledTableCell>
                            <StyledTableCell align="center">User</StyledTableCell>
                            <StyledTableCell align="center"></StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {props.relationships.relationshipList.map((relationship) => (
                            <StyledTableRow
                                key={relationship.mainUser.userName && relationship.relationshipType && relationship.otherUser.userName}>
                                <StyledTableCell
                                    align="center">{relationship.mainUser.userName}</StyledTableCell>
                                <StyledTableCell
                                    align="right">is</StyledTableCell>
                                <StyledTableCell
                                    align="center">{relationship.relationshipType.charAt(0).toUpperCase() + relationship.relationshipType.slice(1).toLowerCase()}</StyledTableCell>
                                <StyledTableCell
                                    align="left">of</StyledTableCell>
                                <StyledTableCell
                                    align="center">{relationship.otherUser.userName}</StyledTableCell>
                                <button onClick={() =>{handleClickEdit(relationship.relationshipId)}}
                                        id="button">Edit
                                </button>
                            </StyledTableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <br/>
            <button onClick={handleClickCreate} id="button">Create</button>
        </div>
    );
}