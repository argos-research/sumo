function color = getColor(vehID)
%getColor Get the color of the vehicle.
%   color = getColor(VEHID) Returns the vehicle's rgba color.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id$

import traci.constants
color = traci.vehicle.getUniversal(constants.VAR_COLOR, vehID);