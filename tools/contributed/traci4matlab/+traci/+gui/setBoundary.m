function setBoundary(viewID, xmin, ymin, xmax, ymax)
%setBoundary Set the coordinates of the view.
%   setBoundary(VIEWID, XMIN, YMIN, XMAX, YMAX) Set the current boundary 
%   for the given view (see getBoundary).

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: setBoundary.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
global message
traci.beginMessage(constants.CMD_SET_GUI_VARIABLE, constants.VAR_VIEW_BOUNDARY, viewID, 1+8+8+8+8);
message.string = [message.string uint8(sscanf(constants.TYPE_BOUNDINGBOX,'%x'))...
    traci.packInt64([ymax xmax ymin xmin])];
traci.sendExact();