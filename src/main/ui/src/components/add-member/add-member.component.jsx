import React, {useContext} from "react";
import {makeStyles} from "@material-ui/core/styles";
import TextField from "@material-ui/core/TextField";
import {KeyboardDatePicker, MuiPickersUtilsProvider} from "@material-ui/pickers";
import DateFnsUtils from "@date-io/date-fns";
import Grid from "@material-ui/core/Grid";
import InputAdornment from '@material-ui/core/InputAdornment'
import AccountCircle from '@material-ui/icons/AccountCircle';
import EmailIcon from '@material-ui/icons/Email';
import PhoneIcon from '@material-ui/icons/Phone';
import HomeIcon from '@material-ui/icons/Home';
import FingerprintIcon from '@material-ui/icons/Fingerprint';
import CakeIcon from "@material-ui/icons/Cake";
import LocationOnIcon from '@material-ui/icons/LocationOn';
import MarkunreadMailboxIcon from '@material-ui/icons/MarkunreadMailbox';
import "../../components/create-relationship/CreateRelationship.styles.css";
import AppContext from "../../context/AppContext";


export default function AddMemberFormulary({parentCallBack}) {
    const { state } = useContext(AppContext);
    const { userId } = state;

    const useStyles = makeStyles((theme) => ({
        root: {
            '& .MuiTextField-root': {
                top: 10,
                margin: theme.spacing(2),
                width: '35ch',
                fontSize: 20,
                align:'center'
            },
        },
    }));

    const style = {
        fontSize: 35,
    }

    const memberName = React.useRef(null);
    const memberVat = React.useRef(null);
    const memberEmail = React.useRef(null);
    const memberPhone = React.useRef(null);
    const memberLocation = React.useRef(null);
    const memberStreet = React.useRef(null);
    const memberPostCode = React.useRef(null);
    const [selectedDate, setSelectedDate] = React.useState(new Date('1990-08-18T21:11:54'));
    const handleDateChange = (date) => {
        setSelectedDate(date);
    };

    const classes = useStyles();

    let submitHandler = e => {
        e.preventDefault()
        const data = {
            name: memberName.current.value,
            vat: memberVat.current.value,
            email: memberEmail.current.value,
            phoneNumber: memberPhone.current.value,
            location: memberLocation.current.value,
            street: memberStreet.current.value,
            postCode: memberPostCode.current.value,
            birthDate: selectedDate.toLocaleDateString(),
            adminId: userId,
        }
        console.log(data)

        parentCallBack(data);
    }

    return (

        <div align='center'>
            <form onSubmit={submitHandler} className={classes.root}>
                <h1 style={style}>Member Data</h1>
                <div>
                    <TextField id="standard-required" label="Name" inputRef={memberName}
                               InputProps={{
                                   startAdornment: (
                                       <InputAdornment position="start">
                                           <AccountCircle/>
                                       </InputAdornment>
                                   ),
                               }}/>
                    <TextField id="standard-required" label="Email"
                               inputRef={memberEmail} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <EmailIcon/>
                            </InputAdornment>
                        ),
                    }}/>
                </div>
                <div>
                    <TextField id="standard-required" label="Vat"
                               inputRef={memberVat} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <FingerprintIcon/>
                            </InputAdornment>
                        ),
                    }}/>
                    <TextField id="standard-basic" label="Phone Number (optional)"
                               inputRef={memberPhone} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <PhoneIcon/>
                            </InputAdornment>
                        ),
                    }}/>
                </div>
                <div>
                    <TextField id="standard-required" label="Street"
                               inputRef={memberStreet} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <HomeIcon/>
                            </InputAdornment>
                        ),
                    }}/>
                    <TextField id="standard-required" label="Location"
                               inputRef={memberLocation} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <LocationOnIcon/>
                            </InputAdornment>
                        ),
                    }}/>
                </div>
                <div>
                    <TextField id="standard-required" label="Postal Code"
                               inputRef={memberPostCode} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <MarkunreadMailboxIcon />
                            </InputAdornment>
                        ),
                    }}/>
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                        <Grid container justify="space-around">
                            <KeyboardDatePicker required
                                                disableToolbar
                                                variant="inline"
                                                format="dd/MM/yyyy"
                                                margin="normal"
                                                id="date-picker-inline"
                                                label="Birth Date"
                                                value={selectedDate}
                                                onChange={handleDateChange}
                                                KeyboardButtonProps={{
                                                    'aria-label': 'change date',
                                                }} InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <CakeIcon/>
                                    </InputAdornment>
                                ),
                            }}
                            />
                        </Grid>
                    </MuiPickersUtilsProvider>
                </div>
                <br/>
                <br/>
                <br/>
                <button id="button" type="submit">Submit</button>
            </form>
        </div>
    );

}