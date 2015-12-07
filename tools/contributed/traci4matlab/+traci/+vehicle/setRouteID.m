function setRouteID(vehID, routeID)
%setRouteID Sets the id of the route for the named vehicle.
%   setRouteID(VEHID,ROUTEID) Sets the id of the route for the named 
%   vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
traci.sendStringCmd(constants.CMD_SET_VEHICLE_VARIABLE, constants.VAR_ROUTE_ID, vehID, routeID);