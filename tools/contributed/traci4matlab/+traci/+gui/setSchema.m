function setSchema(viewID, schemeName)
%setSchema Set the coloring scheme of the view.
%   setSchema(VIEWID, SCHEMENAME) Set the current coloring scheme for the 
%   given view.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setSchema.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendStringCmd(constants.CMD_SET_GUI_VARIABLE, constants.VAR_VIEW_SCHEMA, viewID, schemeName);