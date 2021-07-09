import React from "react";
import TreeItem from "@material-ui/lab/TreeItem";


export default function renderTree(props) {
    console.log(props)
    return(
        <div>
        {props.map((parent) => (
             <TreeItem key={parent.id} nodeId={parent.id} label={parent.name}>
                 {Array.isArray(parent.childCategories) ? parent.childCategories.map((category) => renderTree(category)) : null}
             </TreeItem>))
        }

        </div>
        );
}
