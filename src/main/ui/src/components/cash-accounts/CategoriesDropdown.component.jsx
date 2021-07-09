import React, {useContext, useEffect, useState} from "react";
import {makeStyles} from '@material-ui/core/styles';
import {FormControl, InputLabel, MenuItem, Select} from "@material-ui/core";
import AppContext from "../../context/AppContext";
import {getCategoriesList} from "../../context/CashAccountsService";


const useStyles = makeStyles((theme) => ({
    root: {
        width: '100%',
        maxWidth: 360,
    },
}));

const dropdown = {
    width: '50ch',
}

function CategoriesDropdown({submitCategoryToParent}) {
    const classes = useStyles();
    const inputCategoryId = React.useRef("");

    const {state} = useContext(AppContext);
    const {familyId} = state;

    let submitValue = (event) => {
        event.preventDefault();
        const categoryId = event.target.value;
        console.log(event.target.value)

        submitCategoryToParent(categoryId);
    }

    const defaultFamilyCategories = {categoryDTOs: []}
    const [familyCategories, setFamilyCategories] = useState(defaultFamilyCategories);

    useEffect(() => {
        getCategoriesList().then(response => {
            console.log(response)
            if (response !== null && response !== undefined &&
                response.data !== null && response.data !== undefined) {
                setFamilyCategories(response.data);
            }
        })
    }, []);

    return (
        <div className={classes.root}>
            <FormControl style={dropdown}>
                <InputLabel>Categories:</InputLabel>
                <Select onClick={submitValue} defaultValue="" inputRef={inputCategoryId} required>
                    {familyCategories.categoryDTOs.map((categories) => (
                        <MenuItem value={categories.id}>{categories.name}</MenuItem>
                    ))}
                </Select>
            </FormControl>
        </div>
    );
}

export default CategoriesDropdown;