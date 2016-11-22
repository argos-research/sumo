function color = getColor(vehID)
%getColor Get the color of the vehicle.
%   color = getColor(VEHID) Returns the vehicle's rgba color.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getColor.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
color = traci.vehicle.getUniversal(constants.VAR_COLOR, vehID);