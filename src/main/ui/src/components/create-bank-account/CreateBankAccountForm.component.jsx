import React from "react";
import TextField from '@material-ui/core/TextField';
import {makeStyles} from '@material-ui/core/styles';
import {FormControl, InputLabel, MenuItem, Select} from "@material-ui/core";

const useStyles = makeStyles((theme) => ({
    root: {
        '& .MuiTextField-root': {
            top: 5,
            margin: theme.spacing(1),
            width: '35ch',
            fontSize: 20,
            align: 'center'
        },
    },
}));

function CreateBankAccountForm({parentCallBack1}) {
    const inputDescription = React.useRef("");
    const inputInitialAmount = React.useRef("");
    const inputProvider= React.useRef("");
    const inputCurrency = React.useRef("");

    const classes = useStyles();
    const margin = {
        paddingTop: '50px',
        paddingBottom: '20px',
    }
    const dropdown = {
        top: 20,
        width: '35ch',

    }

    let submitHandler = e => {
        e.preventDefault()
        const data = {
            description: inputDescription.current.value,
            initialAmount: inputInitialAmount.current.value,
            provider: inputProvider.current.value,
            currency: inputCurrency.current.value
        }
        console.log(data)

        parentCallBack1(data);
    }

    return (
        <div align='center' style={margin}>
            <h1>Create a new personal cash account</h1>
            <form onSubmit={submitHandler} className={classes.root}>
                <div>
                    <TextField InputLabelProps={{required: false}} required label="Account description:"
                               inputRef={inputDescription}/>
                </div>
                <br/>
                <div>
                    <TextField InputLabelProps={{required: false}} required label="Initial amount value:"
                               inputRef={inputInitialAmount}/>
                </div>
                <br/>
                <div>
                    <TextField InputLabelProps={{required: false}} required label="Account Provider:"
                               inputRef={inputProvider}/>
                </div>
                <br/>
                <div>
                    <FormControl style={dropdown}>
                        <InputLabel>Currency</InputLabel>
                        <Select defaultValue="" inputRef={inputCurrency} required>Currency>
                            <MenuItem value={1}>EUR</MenuItem>
                            <MenuItem value={2}>USD</MenuItem>
                            <MenuItem value={3}>GBP</MenuItem>
                            <MenuItem value={4}>BRL</MenuItem>
                        </Select>
                    </FormControl>
                </div>
                <br/>
                <br/>
                <br/>
                <button type="submit" id="button">Create</button>
            </form>
        </div>
    )
}

export default CreateBankAccountForm;
