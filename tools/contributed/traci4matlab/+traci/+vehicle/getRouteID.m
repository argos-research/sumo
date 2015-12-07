function routeID = getRouteID(vehID)
%getRouteID Returns the id of the route of the named vehicle.
%   routeID = getRouteID(VEHID) Returns the id of the route of the named 
%   vehicle.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
routeID = traci.vehicle.getUniversal(constants.VAR_ROUTE_ID, vehID);