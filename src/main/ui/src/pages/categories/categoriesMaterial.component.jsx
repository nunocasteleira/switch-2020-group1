import React, {useContext, useEffect, useState} from 'react';
import {makeStyles} from '@material-ui/core/styles';
import TreeView from '@material-ui/lab/TreeView';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import TreeItem from '@material-ui/lab/TreeItem';
import {getStandardCategories} from "../../context/CategoriesService";
import CircularProgress from "@material-ui/core/CircularProgress";
import renderTree from "../../components/standard-category-tree/StandardCategoriesTree.component";
import AppContext from "../../context/AppContext";


const useStyles = makeStyles({
    root: {
        height: 110,
        flexGrow: 1,
        maxWidth: 400,
    },
});

export default function RecursiveTreeView() {
    const classes = useStyles();

    const {state} = useContext(AppContext);
    const {auth} = state;
    const token = auth.token;

    const defaultCategories = {categoriesDTO: []}
    let [categories, setCategories] = useState(defaultCategories);

    const [loading, setLoadingState] = useState(true);

    const [displayErrorMessage, setDisplayErrorMessage] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");


    const renderTree = (nodes) => (
        <TreeItem key={nodes.id} nodeId={nodes.id} label={nodes.name}>
            {Array.isArray(nodes.childCategories) ? nodes.childCategories.map((node) => renderTree(node)) : null}
        </TreeItem>
    );


    useEffect(() => {
        console.log(auth)
        getStandardCategories(token).then(response => {
            console.log(response)
            if (response !== null && response !== undefined &&
                response.data !== null && response.data !== undefined) {
                setCategories(response.data);
            }
        })
        .catch(error => {
            if (error.response !== null && error.response !== undefined &&
                error.response.data !== null && error.response.data !== undefined) {
                setErrorMessage(error.response.data);
            }
            setDisplayErrorMessage(true);
        })
        .finally(function () {
            setLoadingState(false)
        });
    }, []);

    if (loading === true) {
        return <CircularProgress/>
    } else if (displayErrorMessage) {
        return (
            <div align='center'>
                <h1>Oops! Something went wrong</h1>
                <p>{errorMessage}</p>
            </div>
        )
    } else {
        return (
            <div>
                <h1>Categories Tree</h1>

                <TreeView
                    className={classes.root}
                    defaultCollapseIcon={<ExpandMoreIcon/>}
                    defaultExpanded={['root']}
                    defaultExpandIcon={<ChevronRightIcon/>}
                >
                    {categories.categoriesDTO.map((category) => (
                        <TreeItem key={category.id} nodeId={category.id} label={category.name}>
                            {Array.isArray(category.childCategories) ? category.childCategories.map((children) => renderTree(children)) : null}
                        </TreeItem>

                        ))}

                </TreeView>
            </div>
        )
    }
    }


