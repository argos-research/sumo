function setZoom(viewID, zoom)
%setZoom Set the zoom of the view.
%   setZoom(VIEWID, ZOOM) Set the current zoom factor for the given view.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setZoom.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
traci.sendDoubleCmd(constants.CMD_SET_GUI_VARIABLE, constants.VAR_VIEW_ZOOM, viewID, zoom);