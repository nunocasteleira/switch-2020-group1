import React from "react";
import TextField from '@material-ui/core/TextField';
import {makeStyles} from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import DateFnsUtils from '@date-io/date-fns';
import {KeyboardDatePicker, MuiPickersUtilsProvider} from '@material-ui/pickers';
import InputAdornment from '@material-ui/core/InputAdornment'
import AccountCircle from '@material-ui/icons/AccountCircle';
import EmailIcon from '@material-ui/icons/Email';
import PhoneIcon from '@material-ui/icons/Phone';
import HomeIcon from '@material-ui/icons/Home';
import FingerprintIcon from '@material-ui/icons/Fingerprint';
import GroupIcon from '@material-ui/icons/Group';
import CakeIcon from '@material-ui/icons/Cake';
import LocationOnIcon from '@material-ui/icons/LocationOn';
import MarkunreadMailboxIcon from '@material-ui/icons/MarkunreadMailbox';
import "../../components/create-relationship/CreateRelationship.styles.css";

const useStyles = makeStyles((theme) => ({
    root: {
        '& .MuiTextField-root': {
            top: 2,
            margin: theme.spacing(1),
            width: '35ch',
            fontSize: 20,
            align:'center'
        },
    },
}));

export default function AddFamilyFormulary({parentCallback}) {

    const newFamilyName = React.useRef(null);
    const newAdminName = React.useRef(null);
    const newAdminVat = React.useRef(null);
    const newAdminEmail = React.useRef(null);
    const newAdminPhone = React.useRef(null);
    const newAdminLocation = React.useRef(null);
    const newAdminStreet = React.useRef(null);
    const newAdminPostCode = React.useRef(null);
    const [selectedDate, setSelectedDate] = React.useState(new Date('1990-08-18T21:11:54'));
    const handleDateChange = (date) => {
        setSelectedDate(date);
    };

    const style = {
        fontSize: 35,
    }

    const classes = useStyles();

    let submitHandler = e => {
        e.preventDefault()
        const data = {
            familyName: newFamilyName.current.value,
            name: newAdminName.current.value,
            vat: newAdminVat.current.value,
            email: newAdminEmail.current.value,
            phoneNumber: newAdminPhone.current.value,
            location: newAdminLocation.current.value,
            street: newAdminStreet.current.value,
            postCode: newAdminPostCode.current.value,
            birthDate: selectedDate.toLocaleDateString(),
        }
        console.log(data)

        parentCallback(data);
    }

    return (

        <div align='center'>
            <h1 style={style}>Family Data</h1>
            <form onSubmit={submitHandler} className={classes.root}>
                <div>
                    <TextField id="standard-required" label="Name"
                               inputRef={newFamilyName} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <GroupIcon />
                            </InputAdornment>
                        ),}}/>
                </div>
                <h1 style={style}>Administrator Data</h1>
                <div>
                    <TextField id="input-with-icon-textfield" label="Name" inputRef={newAdminName} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <AccountCircle />
                            </InputAdornment>
                            ),}}/>
                    <TextField id="standard-required" label="Email"
                               inputRef={newAdminEmail} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <EmailIcon />
                            </InputAdornment>
                        ),}}/>
                </div>
                <div>
                    <TextField id="standard-required" label="Vat"
                               inputRef={newAdminVat} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <FingerprintIcon />
                            </InputAdornment>
                        ),}}/>
                    <TextField id="standard-basic" label="Phone Number (optional)"
                               inputRef={newAdminPhone} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <PhoneIcon />
                            </InputAdornment>
                        ),}}/>
                </div>
                <div>
                    <TextField id="standard-required" label="Street"
                               inputRef={newAdminStreet} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <HomeIcon />
                            </InputAdornment>
                        ),}}/>
                    <TextField id="standard-required" label="Location"
                               inputRef={newAdminLocation} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <LocationOnIcon />
                            </InputAdornment>
                        ),}}/>
                </div>
                <div>
                    <TextField id="standard-required" label="Postal Code"
                               inputRef={newAdminPostCode} InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <MarkunreadMailboxIcon/>
                            </InputAdornment>
                        ),
                    }}/>
                    <MuiPickersUtilsProvider utils={DateFnsUtils}>
                        <Grid container justify="space-around">
                            <KeyboardDatePicker
                                required
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
                                }}
                                InputProps={{
                                    startAdornment: (
                                        <InputAdornment position="start">
                                            <CakeIcon />
                                        </InputAdornment>
                                    ),}}
                            />
                        </Grid>
                    </MuiPickersUtilsProvider>
                </div>
                <br/>
                <button id="button" type="submit">Submit</button>
            </form>
        </div>
    );

}